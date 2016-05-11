import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.*;
import java.io.*;

public class LesFilLytter implements EventHandler<ActionEvent>{

	Stage vindu;
	TextField tekst;

	public LesFilLytter(Stage v, TextField t){
		vindu = v;
		tekst = t;
	}

	public void handle(ActionEvent ae){

		FileChooser nyFil = new FileChooser();
		nyFil.setTitle("Choose file");
		nyFil.getExtensionFilters().add(new ExtensionFilter("Text files", "*.txt"));
		File fil = nyFil.showOpenDialog(vindu);
		if(fil != null){
			tekst.setText(fil.getPath());
		}
	
	}

}