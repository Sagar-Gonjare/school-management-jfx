package studentNotice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NoticeRecord {
	private SimpleIntegerProperty noticeId;
	private SimpleStringProperty date;
    private SimpleStringProperty title;
    private SimpleStringProperty description;

    @JsonCreator
    public NoticeRecord(@JsonProperty("noticeId") int noticeId,
                      @JsonProperty("date") String date,
                      @JsonProperty("title") String title,
                      @JsonProperty("description") String description) {
        this.noticeId = new SimpleIntegerProperty(noticeId);
        this.date = new SimpleStringProperty(date);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
    }
    
    public int getNoticeId() {
        return noticeId.get();
    }

    public SimpleIntegerProperty noticeIdProperty() {
        return noticeId;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}
