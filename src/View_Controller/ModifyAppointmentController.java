package View_Controller;

import Database.DBConnection;
import Database.DBQuery;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyAppointmentController implements  Initializable {

    Stage stage;
    Parent scene;

    @FXML private TextField apptIdTxt;
    @FXML private TextField custIdTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField titleTxt;
    @FXML private ComboBox typeComboBox;
    @FXML private DatePicker dateDatePicker;
    @FXML private ComboBox startTimeComboBox;
    @FXML private ComboBox endTimeComboBox;

    @FXML TableView<Customer> customerTableView;
    @FXML TableColumn<Customer,String> custIdCol;
    @FXML TableColumn<Customer,String> custNameCol;

    public ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private ObservableList<String> apptTypes = FXCollections.observableArrayList("Sale",
            "Consultation", "Presentation");


    public void sendAppointment(Appointment appointment) {

        apptIdTxt.setText(String.valueOf(appointment.getApptId()));
        custIdTxt.setText(String.valueOf(appointment.getCustId()));
        nameTxt.setText(String.valueOf(appointment.getName()));
        titleTxt.setText(String.valueOf(appointment.getTitle()));
        typeComboBox.getSelectionModel().select(appointment.getType());
        dateDatePicker.setValue(LocalDate.parse(appointment.getDate()));
        startTimeComboBox.getSelectionModel().select(appointment.getStartTime());
        endTimeComboBox.getSelectionModel().select(appointment.getEndTime());

    }

    @FXML void onActionSaveAppointment(ActionEvent event) throws IOException {
        try {
            String apptId = apptIdTxt.getText();
            String custId = custIdTxt.getText();
            String name = nameTxt.getText();
            String title = titleTxt.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem().toString();
            String date = dateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startTime = (String) startTimeComboBox.getValue().toString();
            String endTime = (String) endTimeComboBox.getValue().toString();

            DBQuery.modifyAppointment(apptId, custId, name, title, type, date, startTime, endTime);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch(IOException | SQLException e) {
            System.out.println("Error: " + e);
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @FXML void onActionSelectCust(ActionEvent event) throws IOException {

        Customer addApptCustomer = customerTableView.getSelectionModel().getSelectedItem();

        nameTxt.setText(addApptCustomer.getName());
        custIdTxt.setText(String.valueOf(addApptCustomer.getId()));
    }

    @FXML void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        apptIdTxt.setDisable(true);
        custIdTxt.setDisable(true);
        nameTxt.setDisable(true);

        typeComboBox.setItems(apptTypes);
        startTimeComboBox.setItems(DBQuery.getTimes());
        endTimeComboBox.setItems(DBQuery.getTimes());

        Connection conn;

        try {
            conn = DBConnection.startConnection();
            ResultSet rsCust = conn.createStatement().executeQuery("SELECT customerId, " +
                    "customerName, address, address2, city, postalCode, country, phone " +
                    "FROM customer c INNER JOIN address a ON c.addressId = a.addressId " +
                    "INNER JOIN city i ON a.cityId = i.cityId " +
                    "INNER JOIN country o ON i.countryId = o.countryId ORDER BY customerId");
            while (rsCust.next()) {
                allCustomers.add(new Customer(
                        rsCust.getInt("customerId"),
                        rsCust.getString("customerName"),
                        rsCust.getString("phone"),
                        rsCust.getString("address"),
                        rsCust.getString("city"),
                        rsCust.getString("postalCode"),
                        rsCust.getString("country")));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        customerTableView.setItems(allCustomers);

    }
}
