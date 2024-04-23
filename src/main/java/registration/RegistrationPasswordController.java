package registration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.Login;
import tempData.RegistrationData;
import welcome.Welcome;

public class RegistrationPasswordController
{
	@FXML
	private TextField passcode;
	@FXML
	private TextField confirmPasscode;
	@FXML
	private Button next;
	@FXML
	private Button login;
	@FXML
	private Button back;
	@FXML
	private Button close;
	
	public void back(ActionEvent event) {
		new RegistrationInfo().show();
	}
	
	public void close(ActionEvent event) {
		System.exit(0);
	}
	public void login(ActionEvent event) {
		new Login().show();
	}
	
	RegistrationData t= new RegistrationData();
	private String mobileNumber = t.registrationData[0] ;
	private String grnNumber =  t.registrationData[1];
	
	public void next(ActionEvent event) throws IOException {
	
		
		if (passcode.getText().isEmpty() || 
			confirmPasscode.getText().isEmpty())
	   {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in all the data in all fields.");
			alert.showAndWait();
			return;
		}
		
		if (!passcode.getText().equals(confirmPasscode.getText())) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Passcode And ConfirmPasscode is not Same.");
			alert.showAndWait();
			return;
          }

	
		
		String url = "http://localhost:8080/user/api/v1/registration";
        String jsonPayload = "{\n" + 
				"\"mobileNumber\"" + ":\"" + mobileNumber + "\", \r\n" +
				"\"grnNumber\"" + ":\"" + grnNumber + "\", \r\n" +
				"\"passcode\"" + ":\"" + passcode.getText() + "\" \r\n" +
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
                System.out.println("**Success**");
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                System.out.println("**Error**");

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
            
			
            if(message.equals("User Registered Successfully")) {
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("User Registered Successfully.");
    			alert.showAndWait();
    			new Welcome().show();
            }
            
            if(message.equals("Enter valid mobile number")) {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Enter valid mobile number.");
    			alert.showAndWait();
    			return;
            }
            
            if(message.equals("Mobile Number already registered")) {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Mobile Number already registered.");
    			alert.showAndWait();
    			return;
            }
            
            if(message.equals("Mobile Number and GRN Number is not matching")) {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText(null);
    			alert.setContentText("Mobile Number and GRN Number is not matching.");
    			alert.showAndWait();
    			return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}