package View_Controller;

import Database.DBConnection;
import Database.DBQuery;
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

import javax.management.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;



public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TextField apptIdTxt;
    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField titleTxt;
    @FXML private ComboBox typeComboBox;
    @FXML private DatePicker dateDatePicker;
    @FXML private ComboBox startTimeComboBox;
    @FXML private ComboBox endTimeComboBox;

    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer,String> custIdCol;
    @FXML private TableColumn<Customer,String> custNameCol;

    public ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private ObservableList<String> apptTypes = FXCollections.observableArrayList("Sale",
            "Consultation", "Presentation");

    @FXML void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onActionSelectCust (ActionEvent event) throws IOException {
        Customer addApptCustomer = customerTableView.getSelectionModel().getSelectedItem();

        nameTxt.setText(addApptCustomer.getName());
        idTxt.setText(String.valueOf(addApptCustomer.getId()));

    }

    @FXML void onActionSaveAppointment(ActionEvent event) throws IOException {
        try{
            String name = nameTxt.getText();
            String id = idTxt.getText();
            String title = titleTxt.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem().toString();
            String date = dateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startTime = (String) startTimeComboBox.getValue().toString();
            String endTime = (String) endTimeComboBox.getValue().toString();

            DBQuery.saveAppointment(name, id, title, type, date, startTime, endTime);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException | SQLException e) {
            System.out.println("Error: " + e);
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        apptIdTxt.setDisable(true);
        apptIdTxt.setText("***Auto Generated***");
        idTxt.setDisable(true);
        nameTxt.setDisable(true);

        typeComboBox.setItems(apptTypes);
        typeComboBox.getSelectionModel().selectFirst();

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
