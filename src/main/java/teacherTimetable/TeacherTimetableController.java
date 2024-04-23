package teacherTimetable;

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeTeacher.WelcomeTeacher;



public class TeacherTimetableController {
	 @FXML
	 private Label first_name;
		
	@FXML
	private Label last_name;
	
	@FXML
    private Button back;

    @FXML
    private ComboBox<String> className;

    @FXML
    private Button close;

    @FXML
    private ImageView logo;

    @FXML
    private Button logout;

    @FXML
    private ImageView profile_image;

    @FXML
    private Label profile_name;

    @FXML
    private Button save;

    @FXML
    private Button timetable;
    
    @FXML
    private Button insert;

    @FXML
    private ImageView timetableImage;
    
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
        timetableImage.setImage(image);
        timetableImage.setFitWidth(600);
        timetableImage.setFitHeight(250);
        timetableImage.setPreserveRatio(false);
	}
    
    @FXML
    public void back(ActionEvent event) {
    	new WelcomeTeacher().show();
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
    public void save(ActionEvent event) {
    	if ((className.getValue()).isEmpty()) {
        			
        			Alert alert = new Alert(Alert.AlertType.ERROR);
        			alert.setTitle("Error");
        			alert.setHeaderText(null);
        			alert.setContentText("Please fill in all the data in all fields.");
        			alert.showAndWait();
        			return;
        		}
    	String url = "http://localhost:8080/timetable/api/v1/create";
        String jsonPayload = "{\n" + 
				"\"className\"" + ":\"" + className.getValue() + "\", \r\n" +
				"\"timetableImage\"" + ":\"" + encodedString + "\" \r\n" +
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
			
            if(message.equals("Timetable updated Successfully")) {
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("Timetable updated Successfully.");
    			alert.showAndWait();
    			return;
            } 
            
            if(message.equals("Timetable added Successfully")) {
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    			alert.setTitle("Success");
    			alert.setHeaderText(null);
    			alert.setContentText("Timetable added Successfully.");
    			alert.showAndWait();
    			return;
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];
	
    public void initialize() {
       	// Set values for the ComboBox
    	className.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
		//className.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
		// Set an initial value for the ComboBox
		//comboBox.setValue("Option 1");
		try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/teacher/api/v1/teacherProfile/"+grnNumber);
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
			String profileImage = jsonResponse.getString("teacherProfileImage");
			String teacherFirstName = jsonResponse.getString("teacherFirstName");
			String teacherLastName = jsonResponse.getString("teacherLastName");
			// int count = jsonResponse.getInt("count");
			// JSONArray items = jsonResponse.getJSONArray("items");

			// Process the extracted data as needed
			System.out.println("profileImage= " + profileImage);
			System.out.println("studentFirstName= " + teacherFirstName);
			System.out.println("studentFirstName= " + teacherLastName);
			// System.out.println("Count: " + count);
			// System.out.println("Items: " + items);

			byte[] imageBytes = Base64.getDecoder().decode(profileImage);
			Image image = new Image(new ByteArrayInputStream(imageBytes));

			// Set the image in the ImageView
			profile_image.setImage(image);
			profile_image.setFitWidth(150);
			profile_image.setFitHeight(150);
			profile_image.setPreserveRatio(false);
			
			first_name.setText(teacherFirstName);
			last_name.setText(teacherLastName);

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 	 
	}

}
