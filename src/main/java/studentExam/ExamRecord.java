package studentExam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ExamRecord {
	private SimpleIntegerProperty srNo;
	private SimpleStringProperty examName;
    private SimpleStringProperty className;
    private SimpleStringProperty subject;
    private SimpleStringProperty examDate;
    private SimpleIntegerProperty fullMarks;
    private SimpleIntegerProperty passingMarks;

    @JsonCreator
    public ExamRecord(@JsonProperty("srNo") int srNo,
                      @JsonProperty("examName") String examName,
                      @JsonProperty("className") String className,
                      @JsonProperty("subject") String subject,
                      @JsonProperty("examDate") String examDate,
                      @JsonProperty("fullMarks") int fullMarks,
                      @JsonProperty("passingMarks") int passingMarks) {
        this.srNo = new SimpleIntegerProperty(srNo);
        this.examName = new SimpleStringProperty(examName);
        this.className = new SimpleStringProperty(className);
        this.subject = new SimpleStringProperty(subject);
        this.examDate = new SimpleStringProperty(examDate);
        this.fullMarks = new SimpleIntegerProperty(fullMarks);
        this.passingMarks = new SimpleIntegerProperty(passingMarks);  
    }
    
    
    public int getSrNo() {
        return srNo.get();
    }

    public SimpleIntegerProperty srNoProperty() {
        return srNo;
    }

    public String getExamName() {
        return examName.get();
    }

    public SimpleStringProperty examNameProperty() {
        return examName;
    }

    public String getClassName() {
        return className.get();
    }

    public SimpleStringProperty classNameProperty() {
        return className;
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public String getExamDate() {
        return examDate.get();
    }

    public SimpleStringProperty examDateProperty() {
        return examDate;
    }

    public int getFullMarks() {
        return fullMarks.get();
    }

    public SimpleIntegerProperty fullMarksProperty() {
        return fullMarks;
    }

    public int getPassingMarks() {
        return passingMarks.get();
    }

    public SimpleIntegerProperty passingMarksProperty() {
        return passingMarks;
    }
}
