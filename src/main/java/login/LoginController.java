package login;

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
import principal.Principal;
import registration.RegistrationInfo;
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;
import welcomeTeacher.WelcomeTeacher;

public class LoginController {

	@FXML
	private TextField mobileNumber;

	@FXML
	private PasswordField passcode;

	@FXML
	private Button login;
	
	@FXML
	private Button back;
	
	@FXML
	private Button registration;
	
	@FXML
	private Button close;
	
	public void close(ActionEvent event) {
		 System.exit(0);
	}
	
	public void back(ActionEvent event) {
		new Welcome().show();
	}
	
	public void registration(ActionEvent event) {
		new RegistrationInfo().show();
	}
	
	public void login(ActionEvent event)throws IOException{
		
		if (mobileNumber.getText().isEmpty() || 
			passcode.getText().isEmpty()) {
				
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill in all the data in all fields.");
			alert.showAndWait();
			return;
		}
		
		
		// The message that is going to be sent to the server
		// using the POST request
		final String messageContent = "{\n" + "\"mobileNumber\"" + ":\"" + mobileNumber.getText() + "\", \r\n"
				+ "\"passcode\"" + ":\"" + passcode.getText() + "\" \r\n" +"\n}";
				
				// Printing the message
				System.out.println(messageContent);

	 			// URL of the API or Server
				String url = "http://localhost:8080/user/api/v1/login";
				URL urlObj = new URL(url);
				HttpURLConnection postCon = (HttpURLConnection) urlObj.openConnection();
				postCon.setRequestMethod("POST");
				postCon.setRequestProperty("userId", "abcdef");
				
				// Setting the message content type as JSON
				postCon.setRequestProperty("Content-Type", "application/json");
				postCon.setDoOutput(true);
				
				// for writing the message content to the server
				OutputStream osObj = postCon.getOutputStream();
				osObj.write(messageContent.getBytes());
				
				// closing the output stream
				osObj.flush();
				osObj.close();
				int respCode = postCon.getResponseCode();
				System.out.println("Response from the server is: \n");
				System.out.println("The POST Request Response Code :  " + respCode);
				System.out.println("The POST Request Response Message : " + postCon.getResponseMessage());
				if (respCode == HttpURLConnection.HTTP_CREATED) {
					// reaching here means the connection has been established
					// By default, InputStream is attached to a keyboard.
					// Therefore, we have to direct the InputStream explicitly
					// towards the response of the server
					InputStreamReader irObj = new InputStreamReader(postCon.getInputStream());
					BufferedReader br = new BufferedReader(irObj);
					String input = null;
					StringBuffer sb = new StringBuffer();
					while ((input = br.readLine()) != null) {
						sb.append(input);
					}
					br.close();
					
					/*
					JSONObject jsonResponse = new JSONObject(sb.toString());

					// Extract data from the JSON object
					String grnNumber = jsonResponse.getString("grnNumber");
					// int count = jsonResponse.getInt("count");
					// JSONArray items = jsonResponse.getJSONArray("items");

					// Process the extracted data as needed
					System.out.println("profileImage= " + grnNumber);
					*/
					
					postCon.disconnect();
					// printing the response
					
					System.out.println(sb.toString());
					
					
					
					JSONObject jsonResponse = new JSONObject(sb.toString());

					// Extract data from the JSON object
					int grnNumber = jsonResponse.getInt("grnNumber");
					// int count = jsonResponse.getInt("count");
					// JSONArray items = jsonResponse.getJSONArray("items");
					LoginGrnNumberData t = new LoginGrnNumberData();
					t.setGrnNumber(grnNumber);
					// Process the extracted data as needed
					System.out.println("grnNumber = " + grnNumber);
					
					
					
					if (sb.toString().contains("Teacher")) {
						System.out.println("Welcome Teacher");
						new WelcomeTeacher().show();
					}

					if (sb.toString().contains("Student")) {
						System.out.println("Welcome Student");
						new WelcomeStudent().show();
					}
					
					if (sb.toString().contains("principal")) {
						System.out.println("Welcome Principal");
						new Principal().show();
					}

				} else {

					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Invalid Mobile Number or Password");
					alert.showAndWait();
				}
			}
		}
