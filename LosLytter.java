import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.*;
import javafx.scene.control.Control.*;
import javafx.scene.control.Alert.*;

public class LosLytter implements EventHandler<ActionEvent>{

	TextField tekst;
	Main main;

	public LosLytter(Main m, TextField t){
		main = m;
		tekst = t;
	}

	public void handle(ActionEvent ae){	
		try{
			/*Alert popUp = new Alert(AlertType.INFORMATION);
            popUp.setTitle("Info");
            popUp.setHeaderText(null);
            popUp.setContentText("Solves Sodukoboard in direcory: \n" + tekst.getText());
            popUp.showAndWait();*/
            main.lesFil(tekst.getText());


		}catch(Exception e){
			Alert feil = new Alert(AlertType.ERROR);
            feil.setTitle("ERROR");
            feil.setHeaderText(null);
			feil.setContentText("Could not open file!");
            feil.showAndWait();
		}
	}

}