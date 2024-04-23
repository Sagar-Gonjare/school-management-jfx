package studentTimetable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;
import welcomeTeacher.WelcomeTeacher;

public class StudentTimetableController {

	@FXML
    private Button back;

    @FXML
    private Button close;

    @FXML
    private ImageView logo;

    @FXML
    private Button logout;

    @FXML
    private ImageView profile_image;

    @FXML
	private Label first_name;

	@FXML
	private Label last_name;

    @FXML
    private ImageView timetableImage;

    @FXML
    public void back(ActionEvent event) {
    	new WelcomeStudent().show();
    }

    @FXML
    public void close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    public void logout(ActionEvent event) {
    	new Welcome().show();
    }
    
    
    LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];
	
	private String applyClass;
	
    public void initialize() {
       	// Set values for the ComboBox
    	
		try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/student/api/v1/studentProfile/"+grnNumber);
			System.out.println(url);
			// Open a connection to the API
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set the request method (GET, POST, etc.)
			connection.setRequestMethod("GET");

			// Read the API response
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			
			System.out.println(response.toString());

			// Parse the JSON response
			JSONObject jsonResponse = new JSONObject(response.toString());

			// Extract data from the JSON object
			String studentProfileImage = jsonResponse.getString("studentProfileImage");
			String studentFirstName = jsonResponse.getString("studentFirstName");
			String studentLastName = jsonResponse.getString("studentLastName");
			String studentApplyClass = jsonResponse.getString("applyClass");
			applyClass = studentApplyClass;
			
			// Process the extracted data as needed
			System.out.println("studentProfileImage= " + studentProfileImage);
			System.out.println("studentFirstName= " + studentFirstName);
			System.out.println("studentLastName= " + studentLastName);
			System.out.println("studentApplyClass= " + studentApplyClass);

			byte[] imageBytes = Base64.getDecoder().decode(studentProfileImage);
			Image image = new Image(new ByteArrayInputStream(imageBytes));

			// Set the image in the ImageView
			profile_image.setImage(image);
			profile_image.setFitWidth(150);
			profile_image.setFitHeight(150);
			profile_image.setPreserveRatio(false);
			
			first_name.setText(studentFirstName);
			last_name.setText(studentLastName);

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {	
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/timetable/api/v1/show/"+applyClass);
			System.out.println(url);
			// Open a connection to the API
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set the request method (GET, POST, etc.)
			connection.setRequestMethod("GET");

			// Read the API response
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// Parse the JSON response
			JSONObject jsonResponse = new JSONObject(response.toString());

			// Extract data from the JSON object
			String timetable_Image = jsonResponse.getString("timetableImage");
			
			// Process the extracted data as needed
			System.out.println("timetableImage= " + timetable_Image);
			
			if(timetable_Image.equals("Timetable is not yet created.")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Timetable is not yet created.");
    			alert.showAndWait();
    			return;
    		} 
			
			byte[] imageBytes = Base64.getDecoder().decode(timetable_Image);
			Image image = new Image(new ByteArrayInputStream(imageBytes));

			// Set the image in the ImageView
			timetableImage.setImage(image);
			timetableImage.setFitWidth(600);
			timetableImage.setFitHeight(330);
			timetableImage.setPreserveRatio(false);

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
