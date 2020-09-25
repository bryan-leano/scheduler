package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {

    private int id;
    private String name;
    private String title;
    private String type;
    private String date;
    private String startTime;
    private String endTime;

    public Appointment(int id, String name, String title, String type, String date,
                       String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }




}
