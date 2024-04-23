
 package studentAdmission;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import principal.Principal;
import tempData.AdmissionData;
import welcome.Welcome;

public class AcademicInfoController {

	@FXML
	private DatePicker studentBirthDate;
	
	@FXML
	private ComboBox<String> gender;
	
	@FXML
	private ComboBox<String> applyClass;
	
	@FXML
	private TextField previousSchoolName;

	@FXML
	private TextField studentMobileNumber;

	@FXML
	private TextField email;
	
	@FXML
	private Button save;
	
	@FXML
	private Button logout;
	
	@FXML
	private Button back;

	@FXML
	private Button close;

	@FXML
	private Button insert;
	
	@FXML
	private ImageView profile_image;
	
	public void close(ActionEvent event) {
		 System.exit(0);
	}
	
	public void back(ActionEvent event) {
		new GeneralInfo().show();
	}
	
	public void logout(ActionEvent event) {
		new Welcome().show();
	}
	
	private String encodedString;
	
	
	public void insert(ActionEvent event) throws IOException {
		
		FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        //works
        byte[] fileContent = FileUtils.readFileToByteArray(selectedFile);
        encodedString = Base64.getEncoder().encodeToString(fileContent);
        System.out.println("Encoded String:"+encodedString);
        
        byte[] imageBytes = Base64.getDecoder().decode(encodedString);
        Image image = new Image(new ByteArrayInputStream(imageBytes));
        
        // Set the image in the ImageView
        profile_image.setImage(image);
        profile_image.setFitWidth(150);
        profile_image.setFitHeight(120);
        profile_image.setPreserveRatio(false);
	}
	
	AdmissionData t= new AdmissionData();
	private String studentFirstName = t.admissionData[0];
	private String fatherFirstName = t.admissionData[1];
	private String motherFirstName = t.admissionData[2];
	private String lastName = t.admissionData[3];
	private String parentMobileNumber = t.admissionData[4];
	private String address = t.admissionData[5];
	
	
	
	public void save(ActionEvent event) throws IOException {
		
		if (	gender.getValue().isEmpty() ||
				applyClass.getValue().isEmpty() ||
				previousSchoolName.getText().isEmpty() ||
				studentMobileNumber.getText().isEmpty() ||
				email.getText().isEmpty()) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Please fill in all the data in all fields.");
				alert.showAndWait();
				return;
			}
		
		// The message that is going to be sent to the server
		// using the POST request
		String url = "http://localhost:8080/student/api/v1/admission";
		String jsonPayload = "{\n" + 
										"\"studentFirstName\"" + ":\"" + studentFirstName + "\", \r\n" +
										"\"fatherFirstName\"" + ":\"" + fatherFirstName + "\", \r\n" +
										"\"motherFirstName\"" + ":\"" + motherFirstName + "\", \r\n" +
										"\"lastName\"" + ":\"" + lastName + "\", \r\n" +
										"\"parentMobileNumber\"" + ":\"" + parentMobileNumber + "\", \r\n" +
										"\"address\"" + ":\"" + address + "\", \r\n" +
										"\"studentBirthDate\"" + ":\"" + studentBirthDate.getValue() + "\", \r\n" +
										"\"gender\"" + ":\"" + gender.getValue() + "\", \r\n" +
										"\"applyClass\"" + ":\"" + applyClass.getValue() + "\", \r\n" +
										"\"previousSchoolName\"" + ":\"" + previousSchoolName.getText() + "\", \r\n" +
										"\"studentMobileNumber\"" + ":\"" + studentMobileNumber.getText() + "\", \r\n" +
										"\"email\"" + ":\"" + email.getText() + "\", \r\n" +
										"\"profileImage\"" + ":\"" + encodedString + "\" \r\n" +
										"\n}";

		try {
    		// Create URL object
    		URL apiUrl = new URL(url);
    		
    		// Create connection object
    		HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
    		
    		// Set the necessary HTTP method and headers
    		connection.setRequestMethod("POST");
    		connection.setRequestProperty("Content-Type", "application/json");
    		connection.setRequestProperty("Accept", "application/json");
    		
    		// Enable output and send the JSON payload
    		connection.setDoOutput(true);
    		OutputStream outputStream = connection.getOutputStream();
    		outputStream.write(jsonPayload.getBytes());
    		outputStream.flush();
    		outputStream.close();
    		
    		// Get the response code
    		int responseCode = connection.getResponseCode();
    		
    		// Read the response from the API
    		BufferedReader bufferedReader;
    		if (responseCode >= 200 && responseCode < 300) {
    			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    			System.out.println("*****Success*******");
    		} else {
    			bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
    			System.out.println("*****Error*******");
    		}

    		// Process the response
    		StringBuilder response = new StringBuilder();
    		String line;
    		while ((line = bufferedReader.readLine()) != null) {
    			response.append(line);
            }
    		bufferedReader.close();
    		
    		// Print the response
    		System.out.println("Response Code: " + responseCode);
    		System.out.println("Response Body: " + response.toString());
                
                
    		JSONObject jsonResponse = new JSONObject(response.toString());
    		
    		// Extract data from the JSON object
    		String message = jsonResponse.getString("message");
    		//int count = jsonResponse.getInt("count");
    		//JSONArray items = jsonResponse.getJSONArray("items");

    		// Process the extracted data as needed
    		//System.out.println("message= " + message);
    			
    		if(message.equals("Mobile number already registered")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Mobile number already registered.");
    			alert.showAndWait();
    			return;
    		} 
    		
    		if(message.equals("User Registered Successfully")) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("User Registered Successfully.");
    			alert.showAndWait();
    			new Principal().show();
    		} 
    		
    		if(message.equals("Email is already registered")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Email is already registered.");
    			alert.showAndWait();
    			return;
    		} 
    		new Principal().show();
    	}
		catch (IOException e) {
    		e.printStackTrace();
        }
	}
	
	public void initialize() {
       	// Set values for the ComboBox
		gender.setItems(FXCollections.observableArrayList("Male", "Female", "Others"));
		applyClass.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
		// Set an initial value for the ComboBox
		//comboBox.setValue("Option 1");
	}
}
