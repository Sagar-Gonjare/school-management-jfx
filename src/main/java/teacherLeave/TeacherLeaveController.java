package teacherLeave;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import org.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import leaveApprove.LeaveApprove;
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeTeacher.WelcomeTeacher;

public class TeacherLeaveController {
	
	@FXML
	private Button update;

    @FXML
    private Button back;

    @FXML
    private Button close;

    @FXML
    private Label first_name;

    @FXML
    private TableColumn<LeaveRecord, String> fromDate;

    @FXML
    private TableColumn<LeaveRecord, Integer> grn_number;

    @FXML
    private Label last_name;

    @FXML
    private Button logout;

    @FXML
    private ImageView profile_image;

    @FXML
    private TableColumn<LeaveRecord, String> reason;

    @FXML
    private TableColumn<LeaveRecord, String> status;

    @FXML
    private TableColumn<LeaveRecord, String> studentFirstName;

    @FXML
    private TableColumn<LeaveRecord, String> studentLastName;

    @FXML
    private TableView<LeaveRecord> tableview;

    @FXML
    private Label title;

    @FXML
    private TableColumn<LeaveRecord, String> toDate;

    LoginGrnNumberData t = new LoginGrnNumberData();
   	private long grnNumber = t.loginGrnNumberData[0];

    @FXML
    void back(ActionEvent event) {
    	new WelcomeTeacher().show();
    }

    @FXML
    void close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void logout(ActionEvent event) {
    	new Welcome().show();
    }
    
	@FXML void update(ActionEvent event) { 
		new LeaveApprove().show(); 
	}
	
    
    
    private void displayTableView(List<LeaveRecord> leaveList) {

		grn_number.setCellValueFactory(new PropertyValueFactory<>("grnNumber"));
		fromDate.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
		toDate.setCellValueFactory(new PropertyValueFactory<>("toDate"));
		studentFirstName.setCellValueFactory(new PropertyValueFactory<>("studentFirstName"));
		studentLastName.setCellValueFactory(new PropertyValueFactory<>("studentLastName"));
		reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		
        tableview.getItems().addAll(leaveList);
        
        ObservableList<LeaveRecord> observableExamRecords = FXCollections.observableArrayList(leaveList);
      
        tableview.setItems(observableExamRecords);
    }
    
    
    public void initialize() {
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
			profile_image.setPreserveRatio(true);
			
			first_name.setText(teacherFirstName);
			last_name.setText(teacherLastName);

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} 	 
    	
    	
    	try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/leavedata/api/v1/get");
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

			connection.disconnect();
			System.out.println(response.toString());
			
			ObjectMapper objectMapper = new ObjectMapper();
	        List<LeaveRecord> leaveList;
	        try {
	        	leaveList = objectMapper.readValue(response.toString(), new TypeReference<List<LeaveRecord>>() {});
	        } catch (IOException e) {
	            e.printStackTrace();
	            return;
	        }

	        displayTableView(leaveList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
