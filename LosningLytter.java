import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Control.*;
import javafx.scene.control.Alert.*;

public class LosningLytter implements EventHandler<ActionEvent>{

	Main main;
	Sudokubeholder beholder;
	int[][] verdier;
	int lengde;
	boolean harvist;

	public LosningLytter(Main m, Sudokubeholder b){
		main = m;
		beholder = b;
		harvist = false;
	}

	public void handle(ActionEvent ae){

		try{
			
			if(!harvist){
				Brett tempB = beholder.taUt();
				lengde = tempB.hentLengde();
				verdier = tempB.hentRuteVerdier();

				for(int r = 0; r < lengde; r++){
					for (int k = 0; k < lengde; k++){
						main.settInn(verdier[r][k], r,k);
					}
				}
				harvist = true;
			}else{ 
				throw new Exception("Allerede vist!");
			}	

		}catch(Exception e){
			Alert feil = new Alert(AlertType.ERROR);
			feil.setTitle("ERROR");
			feil.setHeaderText(null);
			feil.setContentText("Already shown. Press show next solution");
			feil.showAndWait();
		}


	}

}