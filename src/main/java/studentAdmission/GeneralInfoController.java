package studentAdmission;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tempData.AdmissionData;
import welcome.Welcome;

public class GeneralInfoController {
	
	@FXML
	private TextField studentFirstName;
	@FXML
	private TextField fatherFirstName;
	@FXML
	private TextField motherFirstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField parentMobileNumber;
	@FXML
	private TextField address;
	@FXML
	private Button next;
	@FXML
	private Button logout;
	@FXML
	private Button back;
	@FXML
	private Button close;
	
	public void close(ActionEvent event) {
		 System.exit(0);
	}
	
	public void back(ActionEvent event) {
		new Admission().show();
	}
	
	public void logout(ActionEvent event) {
		new Welcome().show();
	}
	
	public void next(ActionEvent event) throws IOException {
		
		if (studentFirstName.getText().isEmpty() ||
				fatherFirstName.getText().isEmpty() ||
				motherFirstName.getText().isEmpty() ||
				lastName.getText().isEmpty() ||
				parentMobileNumber.getText().isEmpty() ||
				address.getText().isEmpty()) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Please fill in all the data in all fields.");
				alert.showAndWait();
				return;
			}
		
		
		AdmissionData t = new AdmissionData();
		t.setStudentFirstName(studentFirstName.getText());
		t.setFatherFirstName(fatherFirstName.getText());
		t.setMotherFirstName(motherFirstName.getText());
		t.setLastName(lastName.getText());
		t.setParentMobileNumber(parentMobileNumber.getText());
		t.setAddress(address.getText());
		
		new AcademicInfo().show();
				
	}
}
