package common;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import stage.StageMaster;

public class ScreenCommon {
	public void show() {
		try{
			Parent actorGroup =FXMLLoader.load(getClass().getResource(getClass().getSimpleName()+".fxml"));
			StageMaster.getStage().setScene(new Scene(actorGroup));
			StageMaster.getStage().show();
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
}
