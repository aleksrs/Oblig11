//Deklarere klassen 
public class Boks{

	private Rute[][] ruterIboks; //Alle boksens ruter
	private int nummer; // Nummeret til boksen
	private int boksplasseringRad; //Boksens plassering i forhold til rader
	private int bokspasseringKolonne; // I forhold til kolonne
	private int antallrader; // Antall rader
	private int antallkolonner; // Antall Kolonner
	//Konstruktor
	public Boks(int nr, int r, int k, int plasseringRad, int plasseringKolonne){
		nummer = nr;
		antallrader = r;
		antallkolonner = k; 
		boksplasseringRad = plasseringRad;
		bokspasseringKolonne = plasseringKolonne;

		ruterIboks = new Rute[antallrader][antallkolonner];
	}
	//Setter inn ruten paa riktig plass ved a bruke modulo
	public void settInnRute(Rute r){
		Rute rute = r;
		int ruteRad = rute.hentRadNr();
		int ruteKolonne = rute.hentKolonneNr();

		int rad = ruteRad % antallrader;
		int kolonne = ruteKolonne % antallkolonner;
		//System.out.println("Rute satt inn paa rad: " + rad + " kolonne: " + kolonne);
		ruterIboks[rad][kolonne] = rute;

	}
	//returnere boksens rad
	public int hentBoksRad(){
		return boksplasseringRad;
	}
	//returnerer boksens kolonne
	public int hentBoksKolonne(){
		return bokspasseringKolonne;
	}
	//Returnerer boksens nr
	public int hentNr(){
		return nummer;
	}
	//Metode som sjekker om ett gitt tall finnes i boksen
	public boolean finnesTall(int t){
		for(Rute[] array : ruterIboks){
			for(Rute r : array){
				int tall = r.hentVerdi();
				if(tall == t && tall != 0){
					return true;
				}
			}
		}

		return false;
	}

}