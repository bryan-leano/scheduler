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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;



public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TextField apptIdTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField titleTxt;
    @FXML private ComboBox typeComboBox;
    @FXML private TextField dateTxt;
    @FXML private TextField startTimeTxt;
    @FXML private TextField endTimeTxt;

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

    @FXML void onActionSaveAppointment(ActionEvent event) throws IOException {
        try{
            String name = nameTxt.getText();
            String title = titleTxt.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem().toString();
            String date = dateTxt.getText();
            String startTime = startTimeTxt.getText();
            String endTime = endTimeTxt.getText();

            DBQuery.saveAppointment();


        } catch (NullPointerException | SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        apptIdTxt.setDisable(true);
        apptIdTxt.setText("***Auto Generated***");

        typeComboBox.setItems(apptTypes);
        typeComboBox.getSelectionModel().selectFirst();

        Connection conn;

        try {
            conn = DBConnection.startConnection();
            ResultSet rsCust = conn.createStatement().executeQuery("SELECT customerId, " +
                    "customerName, address, address2, city, postalCode, country, phone " +
                    "FROM customer c INNER JOIN address a ON c.addressId = a.addressId " +
                    "INNER JOIN city i ON a.cityId = i.cityId " +
                    "INNER JOIN country o ON i.countryId = o.countryId ORDER BY customerId");
            while (rsCust.next()) {
                allCustomers.add(new Customer(rsCust.getInt("customerId"),
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

        customerTableView.setItems(allCustomers);

    }

}
