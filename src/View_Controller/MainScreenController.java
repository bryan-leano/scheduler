package View_Controller;

import Database.DBConnection;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private RadioButton weekRBtn;
    @FXML private RadioButton monthRBtn;
    @FXML private RadioButton viewAllRBtn;

    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> apptIdCol;
    @FXML private TableColumn<Appointment, String> customerCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, String> dateCol;
    @FXML private TableColumn<Appointment, String> startTimeCol;
    @FXML private TableColumn<Appointment, String> endTimeCol;

    public ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

    public interface LocalDateTime_Interface {

        LocalDateTime stringToLocalDateTime(String dateTime);

    }

    LocalDateTime_Interface convert = (String dateTime) -> { //Lambda used to convert the UTC datetime from the database to the user's localdatetime
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt =  LocalDateTime.parse(dateTime, format).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        return ldt;
    };


    @FXML void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML void onActionModifyCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML void onActionModifyAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };


    @FXML void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    public void viewAllAppts() {

        Connection conn;
        try {
            appointmentsList.clear();
            conn = DBConnection.startConnection();
            ResultSet rsAppt = conn.createStatement().executeQuery("SELECT appointmentId, " +
                    "customerName, title, type, DATE(start) date, " +
                    "start, end FROM customer c INNER JOIN appointment a ON " +
                    "c.customerId = a.customerId ORDER BY start;");
            while (rsAppt.next()) {
                LocalDateTime zonedStart = convert.stringToLocalDateTime(rsAppt.getString("start"));
                LocalDateTime zonedEnd = convert.stringToLocalDateTime(rsAppt.getString("end"));
                String zonedStartString = zonedStart.toString().substring(11,16);
                String zonedEndString = zonedEnd.toString().substring(11,16);

                appointmentsList.add(new Appointment(
                        rsAppt.getInt("appointmentId"),
                        rsAppt.getString("customerName"),
                        rsAppt.getString("title"),
                        rsAppt.getString("type"),
                        rsAppt.getString("date"),
                        zonedStartString,
                        zonedEndString));
            }
            appointmentTableView.setItems(appointmentsList);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        viewAllAppts();

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }

}
