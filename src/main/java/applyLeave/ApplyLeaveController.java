package applyLeave;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import studentLeave.StudentLeave;
import tempData.LoginGrnNumberData;
import welcome.Welcome;

public class ApplyLeaveController {
	
	@FXML
    private Button back;

    @FXML
    private Button close;

    @FXML
    private Label first_name;

    @FXML
    private DatePicker fromDate;

    @FXML
    private Label last_name;

    @FXML
    private Button logout;

    @FXML
    private ImageView profile_image;

    @FXML
    private TextField reason;

    @FXML
    private Button save;

    @FXML
    private DatePicker toDate;

    LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];
	
    @FXML
    void back(ActionEvent event) {
    	new StudentLeave().show();
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
    void save(ActionEvent event) {
    	
    	if ((reason.getText()).isEmpty()) {
            			
            			Alert alert = new Alert(Alert.AlertType.ERROR);
            			alert.setTitle("Error");
            			alert.setHeaderText(null);
            			alert.setContentText("Please fill in all the data in all fields.");
            			alert.showAndWait();
            			return;
    	}
    	
    	String url = "http://localhost:8080/leave/api/v1/create";
    	String jsonPayload = "{\n" + 
    				"\"grnNumber\"" + ":\"" + grnNumber + "\", \r\n" +
    				//"\"studentFirstName\"" + ":\"" + studentFirstName + "\", \r\n" +
    				//"\"studentLastName\"" + ":\"" + studentLastName + "\", \r\n" +
    				"\"fromDate\"" + ":\"" + fromDate.getValue() + "\", \r\n" +
    				"\"toDate\"" + ":\"" + toDate.getValue() + "\", \r\n" +
    				"\"reason\"" + ":\"" + reason.getText() + "\" \r\n" +
    				//"\"status\"" + ":\"" + "Pending" + "\" \r\n" +
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
    		System.out.println("message= " + message);
    			
    		if(message.equals("Leave Applied Successfully")) {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("Leave Applied Successfully.");
    			alert.showAndWait();
    			return;
    		} 
    		
    		if(message.equals("You have already pending Leave Application")) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("You have already pending Leave Application.");
    			alert.showAndWait();
    			return;
    		} 
    		
    	} catch (IOException e) {
    		e.printStackTrace();
        }
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
