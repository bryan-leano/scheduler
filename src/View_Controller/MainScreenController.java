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
import jdk.nashorn.api.tree.IfTree;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    @FXML void onActionModifyCustomerList(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyCustomerList.fxml"));
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
        scene = FXMLLoader.load(getClass().getResource("ModifyAppointmentList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };


    @FXML void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    public void viewWeekAppts() {
        appointmentTableView.getItems().clear();

        Connection conn;
        try {
            appointmentsList.clear();
            conn = DBConnection.startConnection();
            ResultSet rsWeekAppt = conn.createStatement().executeQuery(String.format("SELECT " +
                    "appointmentId, c.customerId, customerName, title, type, DATE(start) date, " +
                    "start, end FROM customer c INNER JOIN appointment a ON " +
                    "c.customerId = a.customerId WHERE DATE(start) BETWEEN '%s' AND '%s' " +
                    "ORDER BY start", LocalDate.now(), LocalDate.now().plusDays(7)));
            while (rsWeekAppt.next()) {
                LocalDateTime zonedStart = convert.stringToLocalDateTime(rsWeekAppt.getString("start"));
                LocalDateTime zonedEnd = convert.stringToLocalDateTime(rsWeekAppt.getString("end"));
                String zonedStartString = zonedStart.toString().substring(11,16);
                String zonedEndString = zonedEnd.toString().substring(11,16);

                appointmentsList.add(new Appointment(
                        rsWeekAppt.getInt("appointmentId"),
                        rsWeekAppt.getInt("customerId"),
                        rsWeekAppt.getString("customerName"),
                        rsWeekAppt.getString("title"),
                        rsWeekAppt.getString("type"),
                        rsWeekAppt.getString("date"),
                        zonedStartString,
                        zonedEndString));
            }
            appointmentTableView.setItems(appointmentsList);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewMonthAppts() {
        appointmentTableView.getItems().clear();

        Connection conn;
        try {
            appointmentsList.clear();
            conn = DBConnection.startConnection();
            ResultSet rsMonthAppt = conn.createStatement().executeQuery(String.format("SELECT " +
                    "appointmentId, c.customerId, customerName, title, type, DATE(start) date, " +
                    "start, end FROM customer c INNER JOIN appointment a ON " +
                    "c.customerId = a.customerId WHERE DATE(start) BETWEEN '%s' AND '%s' " +
                    "ORDER BY start", LocalDate.now(), LocalDate.now().plusDays(31)));
            while (rsMonthAppt.next()) {
                LocalDateTime zonedStart = convert.stringToLocalDateTime(rsMonthAppt.getString("start"));
                LocalDateTime zonedEnd = convert.stringToLocalDateTime(rsMonthAppt.getString("end"));
                String zonedStartString = zonedStart.toString().substring(11,16);
                String zonedEndString = zonedEnd.toString().substring(11,16);

                appointmentsList.add(new Appointment(
                        rsMonthAppt.getInt("appointmentId"),
                        rsMonthAppt.getInt("customerId"),
                        rsMonthAppt.getString("customerName"),
                        rsMonthAppt.getString("title"),
                        rsMonthAppt.getString("type"),
                        rsMonthAppt.getString("date"),
                        zonedStartString,
                        zonedEndString));
            }
            appointmentTableView.setItems(appointmentsList);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllAppts() {
        appointmentTableView.getItems().clear();

        Connection conn;
        try {
            appointmentsList.clear();
            conn = DBConnection.startConnection();
            ResultSet rsAllAppt = conn.createStatement().executeQuery("SELECT appointmentId, " +
                    "c.customerId, customerName, title, type, DATE(start) date, " +
                    "start, end FROM customer c INNER JOIN appointment a ON " +
                    "c.customerId = a.customerId ORDER BY start;");
            while (rsAllAppt.next()) {
                LocalDateTime zonedStart = convert.stringToLocalDateTime(rsAllAppt.getString("start"));
                LocalDateTime zonedEnd = convert.stringToLocalDateTime(rsAllAppt.getString("end"));
                String zonedStartString = zonedStart.toString().substring(11,16);
                String zonedEndString = zonedEnd.toString().substring(11,16);

                appointmentsList.add(new Appointment(
                        rsAllAppt.getInt("appointmentId"),
                        rsAllAppt.getInt("customerId"),
                        rsAllAppt.getString("customerName"),
                        rsAllAppt.getString("title"),
                        rsAllAppt.getString("type"),
                        rsAllAppt.getString("date"),
                        zonedStartString,
                        zonedEndString));
            }
            appointmentTableView.setItems(appointmentsList);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        viewAllRBtn.setSelected(true);
        viewAllAppts();

        viewAllRBtn.selectedProperty().addListener((options, oldValue, newValue) -> {
            viewAllAppts();
        });

        weekRBtn.selectedProperty().addListener((options, oldValue, newValue) -> {
            viewWeekAppts();
        });

        monthRBtn.selectedProperty().addListener((options, oldValue, newValue) -> {
            viewMonthAppts();
        });

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }

}
