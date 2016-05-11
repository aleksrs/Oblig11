//Deklarerer klassen
public class Rute{	

	private int verdi; //Rutens verdi
	private int radNr; // Rutens radnummer
	private int kolonneNr; // Rutens kolonnenr
	private int lengde; // Lengden paa en rad/kolonne
	private Boks boks; //Rutens boks
	private Rad rad; // Rutens rad
	private Kolonne kolonne; // Rutens Kolonne
	private int[] tempMuligetall; // Array som tar vare paa alle mulige kombinasjoner
	private Rute neste;
	private int fyllUtTeller;
	private Brett brettet;
	private int antallLosninger;
	private Sudokubeholder beholder;

	public Rute(Rad r, Kolonne k, int v){
		rad = r;
		kolonne = k;
		verdi = v;
		lengde = rad.hentLengde();
		radNr = rad.hentRadNr();
		kolonneNr = kolonne.hentKolonneNr();
		tempMuligetall = new int[lengde];
		fyllUtTeller = 0;
		antallLosninger = 0;
	}
	//Returnerer raden
	public Rad hentRad(){
		return rad;
	}
	//Returnerer kolonnen
	public Kolonne hentKolonne(){
		return kolonne;
	}
	//Returnerer boksen
	public Boks hentBoks(){
		return boks;
	}
	//Returnerer verdien
	public int hentVerdi(){
		return verdi;
	}
	//Setter boksen til ruta
	public void settBoks(Boks b){
		boks = b;
	}
	//Henter radnummeret til ruten
	public int hentRadNr(){
		return radNr;
	}
	//henter Kolonnenummeret til ruten
	public int hentKolonneNr(){
		return kolonneNr;
	}

	public void settNestePeker(Rute r){
		neste = r;
	}

	public void settBeholderPeker(Sudokubeholder s){
		beholder = s;
	}

	/*Metdoen gaar spor sin rad, kolonne og boks om den inneholder ett gitt tall.
	Hvis de ikke gjor det, settes tallet inn i arrayet som holder pa mulige tall*/ 
	public int[] finnAlleMuligeTall(){
		int teller = 0;
		
		for(int i = 1; i <= tempMuligetall.length; i++){
			 if(!rad.finnesTall(i) && !kolonne.finnesTall(i) && !boks.finnesTall(i)){
			 	tempMuligetall[teller] = i;
			 	teller++;	
			 }

		}

		int[] muligetall = new int[teller];
		for (int i = 0; i < muligetall.length;i ++ ) {
			muligetall[i] = tempMuligetall[i];
		}
		return muligetall; 

	}
	//Metoden som finner losningene, tar inn ett brett og en beholder (fikk nullpointer ellers for somewhat reason)
	public void fyllUtDenneOgResten(Brett b, Sudokubeholder s) throws Exception{
		brettet = b;
		beholder = s;
		//Print under som printer rutens plass, og mulige verdier
		//System.out.println("\nRuteverdi: " + verdi + " Rad: " + radNr + " kolonne: " + kolonneNr);
		int[] muligeverdier = finnAlleMuligeTall();
		/*System.out.println("Mulige verdier:");
		for(int i = 0; i < muligeverdier.length; i++){
			System.out.println(muligeverdier[i]);
		}*/
		
		/*sjekker om denne ruten har verdi fra før av, har den det hopper den videre. Deretter skjekker den om den 
		er paa siste rute. Etter det sjekker den om den ruten har verdi eller ei, for saa aa sette brettet inn i
		beholderen. Hvis det ikke er siste rute, gaar den inn i en for lokke som prover alle mulige verider til hver rute. Verdiene nulles for hver gang metoden 
		kjorer for aa kunne finne alle mulige losninger */
		if(verdi != 0 && neste != null){
			neste.fyllUtDenneOgResten(brettet, beholder);
			//return;
		}else if(neste == null){
			if(verdi != 0){
				antallLosninger ++;
				if(antallLosninger < 3501){
					//System.out.println("Kaller paa settInn");
					beholder.settInn(brettet);
				}
				//brettet.print();
				beholder.oppdaterLosninger(antallLosninger);
				//System.out.println("antallLosninger: " + antallLosninger);
				verdi = 0;

			} else{
				verdi = muligeverdier[0];
				//System.out.println("Satt inn " + verdi + " Rad: " + radNr + " kolonne: " + kolonneNr);
				antallLosninger ++;
				if(antallLosninger < 3501){
					//System.out.println("Kaller paa sett Inn");
					beholder.settInn(brettet);
				}
				//Prints under
				//brettet.print();
				//System.out.println("antallLosninger: " + antallLosninger);
				beholder.oppdaterLosninger(antallLosninger);
				verdi = 0;
			}	
				
		} else{

			for(int i = 0; i < muligeverdier.length; i++){
				
				verdi = muligeverdier[i];
				//System.out.println("Satt inn " + verdi + " Rad: " + radNr + " kolonne: " + kolonneNr);

				if(neste != null){
					neste.fyllUtDenneOgResten(brettet, beholder);
				} 
				
			}			
		verdi = 0;
		}

	}

}