package studentAdmission;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import principal.Principal;
import teacherAdmission.TeacherAdmission;
import welcome.Welcome;

public class AdmissionController {
	@FXML
	private Button back;
	
	@FXML
	private Button logout;
	
	@FXML
	private Button close;
	
	@FXML
	private Button student;
	
	@FXML
	private Button teacher;
	
	@FXML
    private ImageView profile_image;
	
	public void close(ActionEvent event) {
		 System.exit(0);
	}
	
	public void back(ActionEvent event) {
		new Principal().show();
	}
	
	public void logout(ActionEvent event) {
		new Welcome().show();
	}
	
	public void student(ActionEvent event) {
		new GeneralInfo().show();
	}
	
	public void teacher(ActionEvent event) {
		new TeacherAdmission().show();
	}
	
	public void initialize() {
		
	}
}
