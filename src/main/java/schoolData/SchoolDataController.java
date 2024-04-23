package schoolData;

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
import principal.Principal;
import tempData.LoginGrnNumberData;
import updateTotalFees.UpdateTotalFees;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;

public class SchoolDataController {

	@FXML
	private Button back;
	
	@FXML
	private Button close;
	
	@FXML
	private Button logout;
	
	@FXML
	private ImageView profile_image;

	@FXML
	private Label first_name;
	
	@FXML
	private Label last_name;
	
	@FXML
    private Label totalFees;

    @FXML
    private Label totalStudents;

    @FXML
    private Label totalTeachers;
    
    @FXML
	private Button updateTotalFees;
    
    @FXML
	private Button teacherRecord;
    
    @FXML
	private Button studentRecord;
	
	@FXML
	public void back(ActionEvent event) {
		new Principal().show();
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
	public void updateTotalFees(ActionEvent event) {
		new UpdateTotalFees().show();
	}
	
	@FXML
	public void teacherRecord(ActionEvent event) {
		//new WelcomeStudent().show();
	}
	
	@FXML
	public void studentRecord(ActionEvent event) {
		//new WelcomeStudent().show();
	}
	
	LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];
	
	public void initialize() {
		
		try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/student/api/v1/studentCount");
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
			Long studentCount = jsonResponse.getLong("studentCount");

			// Process the extracted data as needed
			System.out.println("studentCount= " + studentCount);
			
			totalStudents.setText(studentCount.toString());

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 	 
		
		try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/teacher/api/v1/teacherCount");
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
			Long teacherCount = jsonResponse.getLong("teacherCount");

			// Process the extracted data as needed
			System.out.println("teacherCount= " + teacherCount);
			
			totalTeachers.setText(teacherCount.toString());

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/totalFees/api/v1/getTotalFees");
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
			Long total_fees = jsonResponse.getLong("totalFees");
			
			// Process the extracted data as needed
			System.out.println("totalFees= " + total_fees);

			totalFees.setText(total_fees.toString());

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 	 
	}
}
