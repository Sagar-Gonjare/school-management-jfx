package StudentLeaveTable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import welcome.Welcome;

public class StudentLeaveTableController {

	@FXML
    private Button back;
	@FXML
    private Button logout;
	@FXML
    private Button close;
	@FXML
    private ImageView profile_image;
	@FXML
    private Label first_name;
	@FXML
    private Label last_name;
	@FXML
	private TableColumn<LeaveRecord, Integer> grnNumber;
	@FXML
	private TableColumn<LeaveRecord, Integer> fromDate;
	@FXML
	private TableColumn<LeaveRecord, Integer> toDate;
	@FXML
	private TableColumn<LeaveRecord, String> status;
	@FXML
	private TableColumn<LeaveRecord, String> description;
	@FXML
	private TableColumn<LeaveRecord, String> reason;
	@FXML
	private TableView<LeaveRecord> tableview;

	@FXML
    public void back(ActionEvent event) {

	}

    @FXML
    public void close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    public void logout(ActionEvent event) {
    	new Welcome().show();
    }
	
}
