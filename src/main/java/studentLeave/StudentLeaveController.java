package studentLeave;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import applyLeave.ApplyLeave;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tempData.LoginGrnNumberData;
import viewLeave.ViewLeave;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;

public class StudentLeaveController {
	
	@FXML
	private Button applyLeave;

	@FXML
	private Button back;

	@FXML
	private ImageView back_button_image;

	@FXML
	private ImageView back_button_image1;

	@FXML
	private ImageView background_image;

	@FXML
	private Button close;

	@FXML
	private Label first_name;

	@FXML
	private Label last_name;

	@FXML
	private Button leaveStatus;

	@FXML
	private ImageView logo;

	@FXML
	private Button logout;

	@FXML
	private ImageView profile_image;

	@FXML
	private Label title;
	
	LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];

	@FXML
	void applyLeave(ActionEvent event) {
		new ApplyLeave().show(); 
	}

	@FXML
	void back(ActionEvent event) {
		new WelcomeStudent().show();
	}

	@FXML
	void close(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void leaveStatus(ActionEvent event) {
		new ViewLeave().show();
	}

	@FXML
	void logout(ActionEvent event) {
		new Welcome().show();
	}
	
	
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
