package studentExam;

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
import tempData.LoginGrnNumberData;
import welcome.Welcome;
import welcomeStudent.WelcomeStudent;

public class StudentExamController {

	@FXML
	private Button back;

	@FXML
	private TableColumn<ExamRecord, String> className;

	@FXML
	private Button close;

	@FXML
	private TableColumn<ExamRecord, String> examDate;

	@FXML
	private TableColumn<ExamRecord, String> examName;

	@FXML
	private TableColumn<ExamRecord, Integer> fullMarks;

	@FXML
	private ImageView logo;

	@FXML
	private Button logout;

	@FXML
	private TableColumn<ExamRecord, Integer> passingMarks;

	@FXML
	private ImageView profile_image;

	@FXML
	private Label first_name;
	
	@FXML
	private Label last_name;

	@FXML
	private TableColumn<ExamRecord, Integer> srNo;

	@FXML
	private TableColumn<ExamRecord, String> subject;

	@FXML
	private TableView<ExamRecord> tableview;

	@FXML
	private Label title;

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

	private void displayTableView(List<ExamRecord> personList) {

        srNo.setCellValueFactory(new PropertyValueFactory<>("srNo"));
        examName.setCellValueFactory(new PropertyValueFactory<>("examName"));
        className.setCellValueFactory(new PropertyValueFactory<>("className"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        examDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        fullMarks.setCellValueFactory(new PropertyValueFactory<>("fullMarks"));
        passingMarks.setCellValueFactory(new PropertyValueFactory<>("passingMarks"));
     
        tableview.getItems().addAll(personList);
        
        ObservableList<ExamRecord> observableExamRecords = FXCollections.observableArrayList(personList);
      
        tableview.setItems(observableExamRecords);
    }

	
	LoginGrnNumberData t = new LoginGrnNumberData();
	private long grnNumber = t.loginGrnNumberData[0];

	public void initialize() {
		
		try {
			// Create a URL object with the API endpoint
			URL url = new URL("http://localhost:8080/exam/api/v1/show");
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
	        List<ExamRecord> personList;
	        try {
	            personList = objectMapper.readValue(response.toString(), new TypeReference<List<ExamRecord>>() {});
	        } catch (IOException e) {
	            e.printStackTrace();
	            return;
	        }

	        displayTableView(personList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
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
