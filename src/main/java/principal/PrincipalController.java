package principal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import schoolData.SchoolData;
import studentAdmission.Admission;
import teacherResult.TeacherResult;
import welcome.Welcome;

public class PrincipalController {

	@FXML
	private Button admission;
	
	@FXML
	private Button result;
	
	@FXML
	private Button logout;
	
	@FXML
	private Button close;
	
	@FXML
	private Button Dashboard;
	
	@FXML
    private ImageView profile_image;
	
	@FXML
	private Label first_name;
	
	@FXML
	private Label last_name;
	

	public void close(ActionEvent event) {
		System.exit(0);
	}	
	
	public void schoolData(ActionEvent event) {
		new SchoolData().show();
	}
	
	public void logout(ActionEvent event) {
		new Welcome().show();
	}	
	
	public void result(ActionEvent event) {
		new TeacherResult().show();
	}	
	
	public void admission(ActionEvent event) {
		new Admission().show();
	}	
	
	public void initialize() {
		
	}
}
