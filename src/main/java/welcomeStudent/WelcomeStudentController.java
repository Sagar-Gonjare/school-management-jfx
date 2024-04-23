package welcomeStudent;


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
import studentExam.StudentExam;
import studentFees.StudentFees;
import studentLeave.StudentLeave;
import studentNotice.StudentNotice;
import studentResult.StudentResult;
import studentTimetable.StudentTimetable;
import tempData.LoginGrnNumberData;
import welcome.Welcome;

public class WelcomeStudentController {
	
    @FXML
    private Label first_name;

    @FXML
    private Label last_name;

    
	@FXML
    private Button notice;

    @FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private ImageView profile_image;
    
    @FXML
    private Button timetable;
    
    @FXML
    private Button exam;
    
    @FXML
    private Button result;
    
    @FXML
    private Button fees;
    
    @FXML
    private Button leave;

    @FXML
    public void notice(ActionEvent event) {
    	new StudentNotice().show();
    }
    
    @FXML
    public void fees(ActionEvent event) {
    	new StudentFees().show();
    }
    
    @FXML
    public void result(ActionEvent event) {
    	new StudentResult().show();
    }
   
    @FXML
    public void timetable(ActionEvent event) {
    	new StudentTimetable().show();
    }
    
    @FXML
    public void exam(ActionEvent event) {
    	new StudentExam().show();
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
    public void leave(ActionEvent event) {
    	new StudentLeave().show();
    }

	LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];
		
    public void initialize() {
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
			// int count = jsonResponse.getInt("count");
			// JSONArray items = jsonResponse.getJSONArray("items");

			// Process the extracted data as needed
			System.out.println("studentProfileImage= " + studentProfileImage);
			System.out.println("studentFirstName= " + studentFirstName);
			System.out.println("studentLastName= " + studentLastName);
			// System.out.println("Count: " + count);
			// System.out.println("Items: " + items);

			byte[] imageBytes = Base64.getDecoder().decode(studentProfileImage);
			Image image = new Image(new ByteArrayInputStream(imageBytes));

			// Set the image in the ImageView
			profile_image.setImage(image);
			profile_image.setFitWidth(150);
			profile_image.setFitHeight(150);
			profile_image.setPreserveRatio(true);
			
			first_name.setText(studentFirstName);
			last_name.setText(studentLastName);

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 	 
	}
}
