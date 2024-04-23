package studentResult;

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;
import welcomeTeacher.WelcomeTeacher;

public class StudentResultController {

	@FXML
    private Button back;

    @FXML
    private Button close;

    @FXML
    private Label english;

    @FXML
    private Label hindi;

    @FXML
    private Button logout;

    @FXML
    private Label marathi;

    @FXML
    private Label maths;

    @FXML
    private ImageView profile_image;

    @FXML
    private Label profile_name;

    @FXML
    private Button result;

    @FXML
    private Label science;

    @FXML
	private Label first_name;

	@FXML
	private Label last_name;
    
    LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];

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
    	
    	
    	try {	
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/result/api/v1/marksview/"+grnNumber);
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
			String error= jsonResponse.getString("status");
			String message= jsonResponse.getString("message");
			
			
			if(message.equals("Marks has not entered yet")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Marks has not entered yet.");
    			alert.showAndWait();
    			return;
    		} 
			
			String getMarathi= jsonResponse.getString("marathi");
			String getEnglish = jsonResponse.getString("english");
			String getHindi = jsonResponse.getString("hindi");
			String getScience = jsonResponse.getString("science");
			String getMaths = jsonResponse.getString("maths");
			// int count = jsonResponse.getInt("count");
			// JSONArray items = jsonResponse.getJSONArray("items");

			// Process the extracted data as needed
			System.out.println("Marathi = " + getMarathi);
			System.out.println("Hindi = " + getHindi);
			System.out.println("English = " + getEnglish);
			System.out.println("Science = " + getScience);
			System.out.println("Maths = " + getMaths);

			// System.out.println("Count: " + count);
			// System.out.println("Items: " + items);

			// Set the image in the ImageView
			//timetableImage.setImage(image);
			//timetableImage.setFitWidth(600);
			//timetableImage.setFitHeight(250);
			//timetableImage.setPreserveRatio(false);

			marathi.setText(getMarathi);
			english.setText(getEnglish);
			hindi.setText(getHindi);
			science.setText(getScience);
			maths.setText(getMaths);

			
			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
}
