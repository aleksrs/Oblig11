//Deklarerer klassen
public class Rad{

	private Rute[] ruterIRad; //radens ruter
	private int lengde; //Lengde (brukes av rute)
	private int nr; // Rutens nr
	//Konstruktor
	public Rad(int i, int l){
		nr = i;
		lengde = l;
		ruterIRad = new Rute[lengde];
	}
	//Setter inn rute paa angitt plass
	public void settInnRute(int i, Rute r){
		ruterIRad[i] = r;
	}
	//Henter nummeret til raden
	public int hentRadNr(){
		return nr;
	}
	//Metode som sjekker om ett gitt tall finnes i raden
	public boolean finnesTall(int t){
		for(int i = 0; i < ruterIRad.length; i++){
			Rute tempRute = ruterIRad[i];
			int tall = tempRute.hentVerdi();
			if(tall == t){
				return true;
			}
		}
		return false;
	}
	//Brukes av rute
	public int hentLengde(){
		return lengde;
	}

}