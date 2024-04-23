package updateTotalFees;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import principal.Principal;
import schoolData.SchoolData;
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;

public class UpdateTotalFeesController {

	@FXML
    private Button back;

    @FXML
    private Button close;

    @FXML
    private Label first_name;

    @FXML
    private Label last_name;

    @FXML
    private ImageView logo;

    @FXML
    private Button logout;

    @FXML
    private ImageView profile_image;

    @FXML
    private TextField totalFees;

    @FXML
    private Button updateFees;
    
    LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];

    @FXML
    void back(ActionEvent event) {
    	new SchoolData().show();
    }

    @FXML
    void close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void logout(ActionEvent event) {
    	new Welcome().show();
    }

    @FXML
    void updateFees(ActionEvent event) {
    	if ((totalFees.getText()).isEmpty()) {
            			
            			Alert alert = new Alert(Alert.AlertType.ERROR);
            			alert.setTitle("Error");
            			alert.setHeaderText(null);
            			alert.setContentText("Please fill in all the data in all fields.");
            			alert.showAndWait();
            			return;
    	}
    	
    	String url = "http://localhost:8080/totalFees/api/v1/update";
    	String jsonPayload = "{\n" + 
    				"\"totalFees\"" + ":\"" + totalFees.getText() + "\" \r\n" +
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

    		// Process the extracted data as needed
    		System.out.println("message= " + message);
    			
    		if(message.equals("Total Fees Updated")) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("Total Fees Updated.");
    			alert.showAndWait();
    			new SchoolData().show();
    		} 
    		
    	} catch (IOException e) {
    		e.printStackTrace();
        }
    }
    
    public void initialize() {
    	
    }
}
