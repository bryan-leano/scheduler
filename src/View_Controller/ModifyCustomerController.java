package View_Controller;

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
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyCustomerController implements  Initializable {

    Stage stage;
    Parent scene;

    @FXML private TextField custIdTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField phoneTxt;
    @FXML private TextField addressTxt;
    @FXML private ComboBox countryComboBox;
    @FXML private ComboBox cityComboBox;
    @FXML private TextField zipTxt;

    private ObservableList<String> countries = FXCollections.observableArrayList("USA",
            "UK", "Canada");

    private ObservableList<String> usaCities = FXCollections.observableArrayList("New York",
            "Chicago", "Portland");

    private ObservableList<String> ukCities = FXCollections.observableArrayList("London",
            "Manchester", "Liverpool");

    private ObservableList<String> canadaCities = FXCollections.observableArrayList("Toronto",
            "Vancouver", "Montreal");


    public void sendCustomer(Customer customer) {

        custIdTxt.setText(String.valueOf(customer.getId()));
        nameTxt.setText(customer.getName());
        phoneTxt.setText(String.valueOf(customer.getPhone()));
        addressTxt.setText(String.valueOf(customer.getAddress()));
        countryComboBox.getSelectionModel().select(customer.getCountry());
        cityComboBox.getSelectionModel().select(customer.getCity());
        zipTxt.setText(String.valueOf(customer.getZip()));

        if (countryComboBox.getValue().toString().equals("USA")) {
            cityComboBox.setItems(usaCities);
        } else if (countryComboBox.getValue().toString().equals("UK")) {
            cityComboBox.setItems(ukCities);
        } else if (countryComboBox.getValue().toString().equals("Canada")) {
            cityComboBox.setItems(canadaCities);
        }

    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        countryComboBox.setItems(countries);

    }

}
