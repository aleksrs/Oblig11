import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.event.*;

import java.util.*;
import java.io.*;
//Deklarerer klassen 
public class Main extends Application{

    final char tom_rute_tegn = '.'; //Trengs til tegnTilVerdi();
	private int rader;
	private int kolonner;
    private int lengde;
    private int antallRuter;
	private int antallRuteriRad;
	private int antallRuterPaaBrett;
    private int [][] verdier; //Tar vare paa alle innleste verdier
    private Brett brettet;
    private Sudokubeholder beholder;
    private Stage vindu;
    private TextField filPath;
    private GridPane gPaneBrett;
    private BorderPane lerret;

    public Main(){
        beholder = new Sudokubeholder();
    }

    public void start(Stage stage){
        vindu = stage;

        lerret = new BorderPane();

        lerret.setTop(topBox(vindu));
        lerret.setBottom(bottomBox(vindu, lerret));

        Scene scene = new Scene(lerret, 1000, 1000);

        vindu.setTitle("Sudokuloser");
        vindu.setScene(scene);
        vindu.show();


    }

    public HBox topBox(Stage vindu){

        HBox top = new HBox();

        Button lesFil = new Button("Open file");
        lesFil.setPrefSize(200, 30);

        filPath = new TextField();
        filPath.setPrefSize(600,30);

        EventHandler<ActionEvent> lesFilLytter = new LesFilLytter(vindu, filPath);
        lesFil.setOnAction(lesFilLytter);

        Button losFil = new Button("Solve");
        losFil.setPrefSize(200, 30);

        losFil.setOnAction(new LosLytter(this, filPath));


        top.getChildren().addAll(lesFil, filPath, losFil);

        return top;


    }

    public HBox bottomBox(Stage vindu, BorderPane bp){
        HBox bot = new HBox();
        bot.setStyle("-fx-background-color: #696969");
        bot.setSpacing(665);

        Button visLosning = new Button("Show solution");
        visLosning.setPrefSize(200, 30);
        visLosning.setOnAction(new LosningLytter(this, beholder));


        Button visNeste = new Button("Show next solution");
        visLosning.setPrefSize(200, 20);
        visNeste.setOnAction(new VisNesteLytter(this, beholder, vindu, bp));

        bot.getChildren().addAll(visLosning, visNeste);

        return bot;
    }


    //LesFil metode som returnerer ett ferdig oppsatt brett etter at det er lest inn
	public void lesFil(String filnavn) throws Exception{
		Scanner fil = new Scanner(System.in);
        int antallTegn = 0;

		//Sjekker om filnavnet er gyldig
        try{
			fil = new Scanner(new File(filnavn));
		} catch(java.io.FileNotFoundException e){
			System.out.println("Feil filnavn!");
			System.exit(0);
		}
        //Tar inn rader og kolonner
		rader = parseInt(fil.nextLine());
        kolonner = parseInt(fil.nextLine());
        lengde = rader * kolonner;
        antallRuter = lengde * lengde;
        
        try{
          
            if(lengde > 64){
                throw new Exception("ForStortBrettUnntak: For stort brett!");
            }  
        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }     

       
        verdier = new int[lengde][lengde];
        int radteller = 0;
	
		try{
		  //While som gar igjennom radene
			while(fil.hasNextLine()){
			     //Tar inn string og erstatter alle mellomrom
                 String linje = fil.nextLine();
			     linje = linje.replaceAll("\\s", "");
                 /*Lokke som gaar igjennom kolonnene i en rad, og setter dette inn i arrayen
                 Kaster Exception hvis noe er feil*/
			     for(int i = 0; i < linje.length(); i++){
				    char tempChar = linje.charAt(i);
				    int verdi = tegnTilVerdi(tempChar);

				    if(verdi == -1){
                        throw new Exception("Ugyldig tegn i filen");
                    } else if(verdi > lengde){
                        throw new Exception("Tegn utenfor lovlig intervall");
                    } 

                    verdier[radteller][i] = verdi;
                    //System.out.println("Verdi: " + verdi + " Rad: " + radteller + " Kolonne: " + i);
                    antallTegn++;
			     }
                 radteller ++;
            }

            if(antallTegn > antallRuter){
                throw new Exception("For mange tegn i filen");
            }
		
        } catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}	
		//Oppretter og returnerer brettet
        brettet = new Brett(rader, kolonner, verdier);
        brettet.settBeholder(beholder);
        brettet.finnAlleLosninger();
        gPaneBrett = new GridPane();
        int r = 0;
        int k = 0;

        while(r != lengde){
            if(k == lengde){
                k = 0;
            }

            while(k != lengde){
                settInn(verdier[r][k], r, k);
                k++;
            }
        
            r++;
        }

        oppdaterGpane();

	}

    public void hentLosning(){

    }

    public void settInn(int v, int r, int k){
        StackPane nyVerdi = new StackPane();

        int venstreTykkelse = 2;
        int toppTykkelse = 2;
        if(r % kolonner == 0 && r != 0){
            venstreTykkelse = 6;
        }

        if(k % rader == 0 && k != 0){
            toppTykkelse = 6;
        }

        nyVerdi.setBorder(new Border(new BorderStroke(Color.DIMGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(venstreTykkelse,2,2, toppTykkelse))));
        if(v == 0){
            Text node = new Text("");
            node.setFont(Font.font("Verdana", FontPosture.ITALIC, 34));
            nyVerdi.getChildren().add(node);
        } else {
            String verdi = Integer.toString(v);
            Text node = new Text(verdi);
            node.setFont(Font.font("Verdana", FontPosture.ITALIC, 34));
            nyVerdi.getChildren().add(node);
        }
        gPaneBrett.add(nyVerdi, k, r);

    }

    public void oppdaterGpane(){
        for (int i = 0; i<lengde; i++) {
            ColumnConstraints kolonneStr = new ColumnConstraints();
            kolonneStr.setPercentWidth(100.0/lengde);
            gPaneBrett.getColumnConstraints().add(kolonneStr);


            RowConstraints radStr = new RowConstraints();
            radStr.setPercentHeight(100.0/lengde);
            gPaneBrett.getRowConstraints().add(radStr);

     }
        lerret.setCenter(gPaneBrett);
        vindu.sizeToScene();
    }




	public int tegnTilVerdi(char tegn) {
        if (tegn == tom_rute_tegn) {                // tom rute, DENNE KONSTANTEN MAA DEKLARERES
            return 0;
        } else if ('1' <= tegn && tegn <= '9') {    // tegn er i [1, 9]
            return tegn - '0';
        } else if ('A' <= tegn && tegn <= 'Z') {    // tegn er i [A, Z]
            return tegn - 'A' + 10;
        } else if (tegn == '@') {                   // tegn er @
            return 36;
        } else if (tegn == '#') {                   // tegn er #
            return 37;
        } else if (tegn == '&') {                   // tegn er &
            return 38;
        } else if ('a' <= tegn && tegn <= 'z') {    // tegn er i [a, z]
            return tegn - 'a' + 39;
        } else {                                    // tegn er ugyldig
            return -1;
        }
    }	

     public char verdiTilTegn(int verdi, char tom) throws UgyldigVerdiUnntak {
        if (verdi == 0) {                           // tom
            return tom;
        } else if (1 <= verdi && verdi <= 9) {      // tegn er i [1, 9]
            return (char) (verdi + '0');
        } else if (10 <= verdi && verdi <= 35) {    // tegn er i [A, Z]
            return (char) (verdi + 'A' - 10);
        } else if (verdi == 36) {                   // tegn er @
            return '@';
        } else if (verdi == 37) {                   // tegn er #
            return '#';
        } else if (verdi == 38) {                   // tegn er &
            return '&';
        } else if (39 <= verdi && verdi <= 64) {    // tegn er i [a, z]
            return (char) (verdi + 'a' - 39);
        } else {                                    // tegn er ugyldig
            throw new UgyldigVerdiUnntak(verdi);    // HUSK DEFINISJON AV UNNTAKSKLASSE
        }
    }
    //Metode som sjekker om de to forste tallene er gyldige
    public int parseInt(String inn){
    	try{
    		return Integer.parseInt(inn);
    	} catch (NumberFormatException e){
    		System.out.println("Kan ikke konverteres til tall");
    		System.exit(0);
    	}

    	return 0;

    }
    //Printer brett
    public void print()throws Exception{
        brettet.print();
    }
    //Printer alle mulige tall
    public void printAlleMulige(Brett b){
        brettet.printAlleMulige();
    }

    public void settBrett(Brett b){
        brettet = b;
    }

    public Brett hentBrett(){
        return brettet;
    }

    class UgyldigVerdiUnntak extends Exception{
    	UgyldigVerdiUnntak(int v){
    		System.out.println();
    	}
    }

    class UgyldigTegnUnntak extends Exception{
        UgyldigTegnUnntak(int v){
            System.out.println();
        }
    }


    
}