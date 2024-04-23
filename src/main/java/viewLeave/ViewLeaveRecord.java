package viewLeave;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewLeaveRecord {
	private SimpleLongProperty grn_number;
	private SimpleStringProperty fromDate;
    private SimpleStringProperty toDate;
    private SimpleStringProperty studentFirstName;
    private SimpleStringProperty studentLastName;
    private SimpleStringProperty reason;
    private SimpleStringProperty status;
    
    @JsonCreator
    public ViewLeaveRecord(@JsonProperty("grnNumber") long grn_number,
                      @JsonProperty("fromDate") String fromDate,
                      @JsonProperty("toDate") String toDate,
                      @JsonProperty("studentFirstName") String studentFirstName,
                      @JsonProperty("studentLastName") String studentLastName,
                      @JsonProperty("reason") String reason,
                      @JsonProperty("status") String status) {
        this.grn_number = new SimpleLongProperty(grn_number);
        this.fromDate = new SimpleStringProperty(fromDate);
        this.toDate = new SimpleStringProperty(toDate);
        this.studentFirstName = new SimpleStringProperty(studentFirstName);
        this.studentLastName = new SimpleStringProperty(studentLastName);
        this.reason = new SimpleStringProperty(reason);
        this.status = new SimpleStringProperty(status);
    }

    public long getGrnNumber() {
        return grn_number.get();
    }

    public SimpleLongProperty grnNumberProperty() {
        return grn_number;
    }

    public String getFromDate() {
        return fromDate.get();
    }

    public SimpleStringProperty fromDateProperty() {
        return fromDate;
    }

    public String getToDate() {
        return toDate.get();
    }

    public SimpleStringProperty toDateProperty() {
        return toDate;
    }

    public String getstudentFirstName() {
        return studentFirstName.get();
    }

    public SimpleStringProperty studentFirstNameProperty() {
        return studentFirstName;
    }
    
    public String getStudentLastName() {
        return studentLastName.get();
    }

    public SimpleStringProperty studentLastNameProperty() {
        return studentLastName;
    }

    public String getReason() {
        return reason.get();
    }

    public SimpleStringProperty reasonProperty() {
        return reason;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}
