package application;

import javafx.application.Application;
import javafx.stage.Stage;
import stage.StageMaster;
import welcome.Welcome;

public class ApplicationMain extends Application {

	public static void main(String args[]) {
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		StageMaster.setStage(primaryStage);
		new Welcome().show();
	}
}