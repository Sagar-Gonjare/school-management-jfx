package welcomeTeacher;

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import teacherExam.TeacherExam;
import teacherFees.TeacherFees;
import teacherLeave.TeacherLeave;
import teacherNotice.TeacherNotice;
import teacherResult.TeacherResult;
import teacherTimetable.TeacherTimetable;
import tempData.LoginGrnNumberData;
import welcome.Welcome;

public class WelcomeTeacherController {
	
	@FXML
    private Label first_name;
	
	@FXML
    private Label last_name;

    @FXML
    private Button close;

    @FXML
    private Button logout;
    
	@FXML
    private Button notice;
	
	@FXML
    private Button exam;
    
    @FXML
    private Button timetable;
    
    @FXML
    private Button result;

    @FXML
    private Button fees;
    
    @FXML
    private Button leave;

    @FXML
    private ImageView profile_image;

    @FXML
    public void notice(ActionEvent event) {
    	new TeacherNotice().show();
    }
    
    @FXML
    public void fees(ActionEvent event) {
    	new TeacherFees().show();
    }
    
    @FXML
    public void result(ActionEvent event) {
    	new TeacherResult().show();
    }
   
    @FXML
    public void timetable(ActionEvent event) {
    	new TeacherTimetable().show();
    }
    
    @FXML
    public void close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    public void logout(ActionEvent event) {
    	new Welcome().show();
    }
    
    @FXML
    public void exam(ActionEvent event) {
    	new TeacherExam().show();
    }
    
    @FXML
    public void leave(ActionEvent event) {
    	new TeacherLeave().show();
    }


	LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];
		
    public void initialize() {
		
    	try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/teacher/api/v1/teacherProfile/"+grnNumber);
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
			String profileImage = jsonResponse.getString("teacherProfileImage");
			String teacherFirstName = jsonResponse.getString("teacherFirstName");
			String teacherLastName = jsonResponse.getString("teacherLastName");
			// int count = jsonResponse.getInt("count");
			// JSONArray items = jsonResponse.getJSONArray("items");

			// Process the extracted data as needed
			System.out.println("profileImage= " + profileImage);
			System.out.println("studentFirstName= " + teacherFirstName);
			System.out.println("studentFirstName= " + teacherLastName);
			// System.out.println("Count: " + count);
			// System.out.println("Items: " + items);

			byte[] imageBytes = Base64.getDecoder().decode(profileImage);
			Image image = new Image(new ByteArrayInputStream(imageBytes));

			// Set the image in the ImageView
			profile_image.setImage(image);
			profile_image.setFitWidth(150);
			profile_image.setFitHeight(150);
			profile_image.setPreserveRatio(true);
			
			first_name.setText(teacherFirstName);
			last_name.setText(teacherLastName);

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 	 
    }
}
