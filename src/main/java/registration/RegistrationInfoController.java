package registration;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import login.Login;
import tempData.RegistrationData;
import welcome.Welcome;

public class RegistrationInfoController {
	
	@FXML
	private TextField mobileNumber;
	@FXML
	private TextField grnNumber;
	@FXML
	private Button next;
	@FXML
	private Button login;
	@FXML
	private Button back;
	@FXML
	private Button close;
	
	public void close(ActionEvent event) {
		 System.exit(0);
	}
	
	public void back(ActionEvent event) {
		new Welcome().show();
	}
	
	public void login(ActionEvent event) {
		new Login().show();
	}
	
	public void next(ActionEvent event) throws IOException {
	
		if (mobileNumber.getText().isEmpty() || 
			grnNumber.getText().isEmpty()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in all the data in all fields.");
			alert.showAndWait();
			return;
		}

		
		RegistrationData t = new RegistrationData();
		t.setGrnNumber(grnNumber.getText());
		t.setMobileNumber(mobileNumber.getText());
		new RegistrationPassword().show();			
	}
		
}	

