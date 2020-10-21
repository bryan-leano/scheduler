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

public class ModifyAppointmentListController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TableView<Appointment> AppointmentTableView;
    @FXML private TableColumn<Appointment,String> ApptIdCol;
    @FXML private TableColumn<Appointment,String> custNameCol;
    @FXML private TableColumn<Appointment,String> titleCol;
    @FXML private TableColumn<Appointment,String> dateCol;

    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public interface LocalDateTime_Interface {

        LocalDateTime stringToLocalDateTime(String dateTime);

    }

    MainScreenController.LocalDateTime_Interface convert = (String dateTime) -> { //Lambda used to convert the UTC datetime from the database to the user's localdatetime
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime ldt =  LocalDateTime.parse(dateTime, format).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        return ldt;
    };

    @FXML void onActionModifyAppointment(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyAppointment.fxml"));
        loader.load();

        ModifyAppointmentController AppointmentInfoController = loader.getController();
        AppointmentInfoController.sendAppointment(AppointmentTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        Connection conn;

        try {
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

                allAppointments.add(new Appointment(
                        rsAppt.getInt("appointmentId"),
                        rsAppt.getString("customerName"),
                        rsAppt.getString("title"),
                        rsAppt.getString("type"),
                        rsAppt.getString("date"),
                        zonedStartString,
                        zonedEndString));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        AppointmentTableView.setItems(allAppointments);

    }

}
