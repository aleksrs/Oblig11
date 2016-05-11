//Importerer 
import java.util.*;
import java.io.*;
import java.lang.*;

//Deklarerer klassen 
public class Brett{

	private int antallRader; // Antall rader i en boks
	private int antallKolonner; // Antall Kolonner i en boks
	private int lengde; // Lengden paa en rad/kolonne
	private int antallruter;
	private Rad[] rader; // En Array til aa holde paa rader
	private Kolonne[] kolonner; // En Array til aa holde paa Kolonner
	private  Boks[] bokser; // Holder paa bokser
	private int[][] datastruktur; // Holder paa alle verdiene som kommer fra filen
	private  Rute[][] brett; // Holder paa alle rutene i brettet
	private char tom = ' '; // Brukes med verdiTilTegn
	private Sudokubeholder beholder;

	/*Konstruktor som tar inn rader, kolonner og en to-dimensjonal array 
	som holder paa alle verdiene i brettet som blir lest inn fra fil. Konstruktoren
	kaller ogsaa paa datastruktur()*/
	public Brett(int r, int k, int[][] rutearray){
		antallRader = r;
		antallKolonner = k;
		datastruktur = rutearray;
		opprettDatastruktur();
		settNeste();
	}	
		
	/* Metode som oppretter og setter inn en ny rute. Tar imot rutens radplass, kolonneplass
	og verdi. Henter ut raden og kolonnen, og oppretter en ny rute. Deretter bruker den ruten
	til aa finne hvilken plass boksen er paa med metoden hentBoksNr(); Deretter setter den boksen
	inn i listene, og setter boksen til ruten */
	public void nyRute(int radPlass, int kolonnePlass, int v){
		Rad tempRad = rader[radPlass];
		Kolonne tempKolonne = kolonner[kolonnePlass];
		int verdi = v;
		Rute nyRute = new Rute(tempRad, tempKolonne, verdi);
		Boks tempBoks = bokser[hentBoksNr(nyRute)];

		tempRad.settInnRute(kolonnePlass, nyRute);
		tempKolonne.settInnRute(radPlass, nyRute);
		tempBoks.settInnRute(nyRute);
		nyRute.settBoks(tempBoks);

		//System.out.println(nyRute.hentVerdi() + " satt inn i boks nr: " + tempBoks.hentNr());
		
		brett[radPlass][kolonnePlass] = nyRute;


	}

	/* Bruker rutens rad og kolonne til aa finne ut hvilken boks den tilhører.
	Metoden returnerer saa tallet boksen har */
	public int hentBoksNr(Rute r){

		int rutensBoksRad = r.hentRadNr() / antallRader;
		int rutensBoksKolonne = r.hentKolonneNr() / antallKolonner;

		int boksnr = rutensBoksKolonne + (rutensBoksRad * antallRader);
		return boksnr;

	}	
	
	//Metode for aa opprette datastruktur		
	public void opprettDatastruktur(){
		//Setter lengden
		lengde = antallRader * antallKolonner;
		antallruter = lengde * lengde;
		
		//Oppretter arrayer med lengden
		brett = new Rute[lengde][lengde];
		rader = new Rad[lengde];
		kolonner = new Kolonne[lengde];
		bokser = new Boks[lengde];
		
		int kolonneteller = 0;
		int radteller = 0;

		//En lokke som opretter riktig antall kolonner, rader og bokser
		for (int i = 0; i < lengde ; i++ ) {
			rader[i] = new Rad(i,lengde);
			kolonner[i] = new Kolonne(i,lengde);

			//Sorger for at boksene opprettes riktig
			if(radteller > antallKolonner-1){
				radteller = 0;
				kolonneteller ++;
			}
			
			bokser[i] = new Boks(i, antallRader, antallKolonner, radteller, kolonneteller);
			Boks temp = bokser[i];
			//System.out.println("Nr: " + temp.hentNr() + " Rad: " + temp.hentBoksRad() + " kolonne: " + temp.hentBoksKolonne());
			radteller ++;
		}

		kolonneteller = 0;
		radteller = 0;
		int antallruter = lengde * lengde;

		//Gaar igjennom alle rutene som finnes paa brettet, henter ut disse for a saa kalle paa nyRute();
		for(int i = 0; i < antallruter; i++){

			if(kolonneteller > lengde - 1){
				kolonneteller = 0;
				radteller ++;

			}
			int ruteverdi = datastruktur[radteller][kolonneteller];
			nyRute(radteller, kolonneteller, ruteverdi);
			kolonneteller ++;
		}

	}

	/*To tellere gaar igjennom hele brettet og printer | og - for hver gang resten
	av telleren/antallrader(kolonner) = 0. Hvis den ikke skal printe - printer den tall, og 
	omvendt. */
		public void print() throws Exception{
			int teller = 0;
			int teller2 = 0;

			for(Rute[] array : brett){
				
				if(teller2 % antallRader == 0 && teller != 0){
						for (int i = 0; i < lengde ;i++ ) {
							if(i % antallKolonner == 0 && i != 0){
								System.out.print("+-");
							} else {
								System.out.print("-");
							}	
						}
						System.out.println();
					}
					
					teller = 0;
				for(Rute r : array){
					if(teller % antallKolonner == 0 && teller != 0 ){
						System.out.print("|");
					}


					teller ++;
					if(r.hentVerdi() == 0){
						System.out.print(".");
					} else {
						System.out.print(verdiTilTegn(r.hentVerdi(), tom) + "");
					}
	
				}
				System.out.println();
				teller2++;


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

      class UgyldigVerdiUnntak extends Exception{
    	UgyldigVerdiUnntak(int v){
    		System.out.println();
    	}
    }
    /*En metode som printer alle mulige tall til rutene. Gaar igjennom
    og printer kun hvis verdien ikke er 0 */
    public void printAlleMulige(){
    	int[] alleMulige = new int[lengde];
    	for(Rute[] array : brett){
    		for(Rute r : array){
    			if(r.hentVerdi() == 0){
    				alleMulige = r.finnAlleMuligeTall();
    				System.out.println("\nRute Rad: " + r.hentRadNr() + " kolonne: " + r.hentKolonneNr() + " Boks: " + r.hentBoks().hentNr());
    				for(int i = 0; i < alleMulige.length; i++){
    					if(alleMulige[i] != 0){
    						System.out.print(alleMulige[i] + ",");
    					}	
       				}
    			}	
    		}
    	}
    }
    /* Metode som gaar igjennom alle rutene, og setter nestepekere
    til alle rutene paa brettet */
    public void settNeste(){
    	ArrayList<Rute> ruteliste = new ArrayList<Rute>();
    	for(Rute[] array : brett){
    		for(Rute r : array){
    			ruteliste.add(r);
    		}
    	}
    	int teller = 0;
    	for(Rute r : ruteliste){
    		if(teller == ruteliste.size() - 1){
    			return;
    		}

    		r.settNestePeker(ruteliste.get(teller + 1));
    		teller ++;

    	}   
    }
    //Metode som finner alle losninger
    public void finnAlleLosninger() throws Exception{
    	Rute tempRute = brett[0][0];
    	tempRute.fyllUtDenneOgResten(this, beholder);

    }
    //Metode som setter peker til beholder
    public void settBeholder(Sudokubeholder s){
    	beholder = s;
    }

    /*Metode som gaar igjennom alle rutene, henter ut verdiene
    og returnerer disse i en dobbel array */
    public int[][] hentRuteVerdier(){

    	
    	int[][] ruter = new int[lengde][lengde];
    	int kolonneteller = 0;
    	int radteller = 0;
    	int teller = 0;

    	while(radteller != lengde){
    		if(kolonneteller == lengde){
    			kolonneteller = 0;
    		}

    		while(kolonneteller != lengde){

    			Rute tempRute = brett[radteller][kolonneteller];
    			ruter[radteller][kolonneteller] = tempRute.hentVerdi();
    			//System.out.println("Verdi hentet ut fra brett: " + ruter[radteller][kolonneteller]);
    			kolonneteller ++;
    		}
    		radteller++;

    	}
    	


    	return ruter;
    }

    public int hentAntallRader(){
    	return antallRader;
    }

    public int hentAntallKolonner(){
    	return antallKolonner;
    }

    public int hentLengde(){
    	return lengde;
    }

}