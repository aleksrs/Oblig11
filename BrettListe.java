import java.util.*;
//En helt vanlig lenkeliste som setter inn foran
public class BrettListe<Brett> implements Iterable<Brett>{

	Node foran;
	private int lengde = 0;
	
	class Node{
		Brett data;
		Node neste;

		public Node(Brett b){
			data = b;
		}
	}

	public void settInn(Brett b){
		Node nyNode = new Node(b);

		if(foran == null){
			foran = nyNode;
		} else{
			nyNode.neste = foran;
			foran = nyNode;
		}

		lengde ++;

	}

	public int hentAntallLosninger(){
		return lengde;
	}

	public BrettIterator iterator(){
		return new BrettIterator();
	}

	class BrettIterator implements Iterator<Brett>{

		Node sjekk = foran;
		Node prev;

		public boolean hasNext(){
			if(sjekk == null){
				return false;
			} else{
				prev = sjekk;
				sjekk = sjekk.neste;
				return true;
			}
		}

		public Brett next(){
			return prev.data;
		}

	}

}