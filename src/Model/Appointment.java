package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {

    private int apptId;
    private int custId;
    private String name;
    private String title;
    private String type;
    private String date;
    private String startTime;
    private String endTime;

    public Appointment(int apptId, int custId, String name, String title, String type, String date,
                       String startTime, String endTime) {
        this.apptId = apptId;
        this.custId = custId;
        this.name = name;
        this.title = title;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getApptId() {
        return apptId;
    }

    public int getCustId() {
        return custId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDate() { return date ; }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setApptId(int id) {
        this.apptId = id;
    }

    public void setCustId(int id) {
        this.custId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
