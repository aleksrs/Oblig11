import java.util.*;

//Objekt som holder orden paa alle brett
public class Sudokubeholder{
	//Oppretter en lenkeliste
	//private BrettListe<Brett> liste;
	private ArrayList<Brett> liste;
	private int antallRader;
	private int antallKolonner;
	private int muligeLosninger;
	private int hentetTeller;
	//Konstruktor
	public Sudokubeholder(){
		//liste = new BrettListe<Brett>();
		liste = new ArrayList<Brett>();
		hentetTeller = 0;
		muligeLosninger = 0;
	}
	/* Metoden tar imot ett lost brett, kaller paa en metode som henter ut
	alle ruteverdiene til det brettet i en double array. Bruker dette til aa 
	opprette ett nytt brett, med de verdiene som saa settes inn i listen*/
	public void settInn(Brett b){

		int[][] verdier  = b.hentRuteVerdier();

		antallRader = b.hentAntallRader();
		antallKolonner = b.hentAntallKolonner();
		 
		liste.add(new Brett(antallRader, antallKolonner, verdier));
		//System.out.println("Losning satt inn!");
	}
	/* Sto ingen ting om hva denne metoden skal gjore, derfor printer den bare a
	alle brettene som ligger i listen*/
	public Brett taUt() throws Exception{
		Brett retur = liste.get(hentetTeller);
		//System.out.println("hentetTeller: " + hentetTeller);
		hentetTeller ++;
		return retur;

	}
	//Printer linjene
	public void antallLosninger(){
		System.out.println("Antall losninger satt inn : " + liste.size());
		System.out.println("Antall mulige losninger: " + muligeLosninger);

	}
	//Metode som oppdaterer antall mulige losninger
	public void oppdaterLosninger(int i){
		muligeLosninger = i;
	}

	public int hentSize(){
		return liste.size();
	}

	public int hentHentet(){
		return hentetTeller;
	}


}