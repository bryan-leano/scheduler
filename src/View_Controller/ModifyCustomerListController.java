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

public class ModifyCustomerListController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer,String> custIdCol;
    @FXML private TableColumn<Customer,String> custNameCol;
    @FXML private TableColumn<Customer,String> custAddressCol;
    @FXML private TableColumn<Customer,String> custPhoneCol;

    public ObservableList<Customer> allCustomers = FXCollections.observableArrayList();


    @FXML void onActionModifyCustomer(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyCustomer.fxml"));
        loader.load();

        ModifyCustomerController CustomerInfoController = loader.getController();
        CustomerInfoController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

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
                        rsCust.getInt("postalCode"),
                        rsCust.getString("country")));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTableView.setItems(allCustomers);

    }

}
