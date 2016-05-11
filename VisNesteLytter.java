import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Control.*;
import javafx.scene.control.Alert.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

public class VisNesteLytter implements EventHandler<ActionEvent>{

	Main main;
	Sudokubeholder beholder;
	Stage vindu;
	BorderPane lerret;
	int lengde;
	int[][] verdier;
	int size;
	int rader;
	int kolonner;


	public VisNesteLytter(Main m, Sudokubeholder b, Stage v, BorderPane bp){
		main = m;
		beholder = b;
		size = beholder.hentHentet();
		vindu = v;
		lerret = bp;

	}

	public void handle(ActionEvent ae){
		try{
			if(size != beholder.hentSize()){	
				try{
			
					Brett tempB = beholder.taUt();
					rader = tempB.hentAntallRader();
					kolonner = tempB.hentAntallKolonner();
					lengde = tempB.hentLengde();
					verdier = tempB.hentRuteVerdier();
					GridPane tempGrid = lagNytt();

					for(int r = 0; r < lengde; r++){
						for (int k = 0; k < lengde; k++){
							settInn(tempGrid, verdier[r][k], r, k);
						}
					}

					oppdaterGrid(tempGrid);

				}catch(Exception e){
					Alert feil = new Alert(AlertType.ERROR);
					feil.setTitle("ERROR");
					feil.setHeaderText(null);
					feil.setContentText("Could not find a solution!");
					feil.showAndWait();
				}
			} else {
				throw new Exception("No more solutions!");
			}
		}catch(Exception ea){
			Alert tomt = new Alert(AlertType.ERROR);
			tomt.setTitle("ERROR");
			tomt.setHeaderText(null);
			tomt.setContentText("There are no more solutions");
			tomt.showAndWait();
		}	 	
	}

	 public void settInn(GridPane g, int v, int r, int k){
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
        g.add(nyVerdi, k, r);

    }

	public GridPane lagNytt(){
		return new GridPane();
	}

	public void oppdaterGrid(GridPane n){
		for (int i = 0; i<lengde; i++) {
            ColumnConstraints kolonneStr = new ColumnConstraints();
            kolonneStr.setPercentWidth(100.0/lengde);
            n.getColumnConstraints().add(kolonneStr);


            RowConstraints radStr = new RowConstraints();
            radStr.setPercentHeight(100.0/lengde);
            n.getRowConstraints().add(radStr);

     }
        lerret.setCenter(n);
        vindu.sizeToScene();
	}



}