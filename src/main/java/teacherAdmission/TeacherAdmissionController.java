package teacherAdmission;

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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import principal.Principal;
import studentAdmission.Admission;
import welcome.Welcome;

public class TeacherAdmissionController {

    @FXML
    private Button back;

    @FXML
    private Button close;

    @FXML
    private Button insert;

    @FXML
    private Button logout;

    @FXML
    private Button save;

    @FXML
    private TextField teacherLastName;

    @FXML
    private TextField teacherAddress;

    @FXML
    private TextField teacherEducation;

    @FXML
    private TextField teacherEmail;

    @FXML
    private TextField teacherExperience;

    @FXML
    private TextField teacherFirstName;

    @FXML
    private TextField teacherMobileNumber;

    @FXML
    private ImageView teacherProfileImage;
    
	private String encodedString;
    
	@FXML
    public void back(ActionEvent event) {
    	new Admission().show();
    }

    @FXML
    public void close(ActionEvent event) {
    	System.exit(0);
    }

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
        teacherProfileImage.setImage(image);
        teacherProfileImage.setFitWidth(120);
        teacherProfileImage.setFitHeight(80);
        teacherProfileImage.setPreserveRatio(false);
    }

    @FXML
    void logout(ActionEvent event) {
    	new Welcome().show();
    }

    @FXML
    public void save(ActionEvent event) throws IOException {
    	// The message that is going to be sent to the server
    	// using the POST request
    	
    	
    	if ((teacherFirstName.getText()).isEmpty() || 
    			(teacherLastName.getText()).isEmpty() || 
    			(teacherEducation.getText()).isEmpty() || 
    			(teacherExperience.getText()).isEmpty() || 
    			(teacherEmail.getText()).isEmpty() ||
    			(teacherMobileNumber.getText()).isEmpty() ||
            	(teacherAddress.getText()).isEmpty()) {
            			
            			Alert alert = new Alert(Alert.AlertType.ERROR);
            			alert.setTitle("Error");
            			alert.setHeaderText(null);
            			alert.setContentText("Please fill in all the data in all fields.");
            			alert.showAndWait();
            			return;
    	}
    	
    	
    	String url = "http://localhost:8080/teacher/api/v1/admission";
    	String jsonPayload =  "{\n" + 
				"\"firstName\"" + ":\"" + teacherFirstName.getText() + "\", \r\n" +
				"\"lastName\"" + ":\"" + teacherLastName.getText() + "\", \r\n" +
				"\"education\"" + ":\"" + teacherEducation.getText() + "\", \r\n" +
				"\"teachingExperience\"" + ":\"" + teacherExperience.getText() + "\", \r\n" +
				"\"mobile\"" + ":\"" + teacherMobileNumber.getText() + "\", \r\n" +
				"\"email\"" + ":\"" + teacherEmail.getText() + "\", \r\n" +
				"\"address\"" + ":\"" + teacherAddress.getText() + "\", \r\n" +
				"\"teacherProfileImage\"" + ":\"" + encodedString + "\" \r\n" +
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
    		String message = jsonResponse.getString("massage");
    		//int count = jsonResponse.getInt("count");
    		//JSONArray items = jsonResponse.getJSONArray("items");

    		// Process the extracted data as needed
    		//System.out.println("message= " + message);
    			
    		if(message.equals("Mobile Number is already registered")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Mobile Number is already registered.");
    			alert.showAndWait();
    			return;
    		} 
    		
    		if(message.equals("Email is already registered")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Email is already registered.");
    			alert.showAndWait();
    			return;
    		} 
    		
    		
    		if(message.equals("Teacher's Data Added Successfully")) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("Teacher's Data Added Successfully.");
    			alert.showAndWait();
    			new Principal().show();
    		} 
    		new Principal().show();
    	} catch (IOException e) {
    		e.printStackTrace();
        }
	}
}
