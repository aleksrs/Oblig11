//Oppretter klassen 
public class Kolonne{

	private  Rute[] ruterIkolonne;//Kolonnens ruter
	private  int nr; //Nummeret til kolonnen
	//Konstruktor
	public Kolonne(int i, int l){
		nr = i;
		ruterIkolonne = new Rute[l];
	}
	//setter inn rute paa angitt plass
	public void settInnRute(int i, Rute r){
		ruterIkolonne[i] = r;
	}
	//Returnerer nummeret
	public int hentKolonneNr(){
		return nr;
	}
	//Sjekker om ett gitt tall finnes i kolonnen
	public boolean finnesTall(int t){
		for (int i = 0; i < ruterIkolonne.length; i++ ) {
			Rute tempRute = ruterIkolonne[i];
			int tall = tempRute.hentVerdi();
			if(tall == t && tall != 0){
				return true;
			}
		}

		return false;
	}

}