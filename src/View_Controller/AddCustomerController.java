package View_Controller;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import Database.DBQuery;


public class AddCustomerController implements Initializable {

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

    @FXML void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onActionSaveCustomer(ActionEvent event) throws IOException, NullPointerException {
        
        try {
            String name = nameTxt.getText();
            String phone = phoneTxt.getText();
            String address = addressTxt.getText();
            String country = countryComboBox.getSelectionModel().getSelectedItem().toString();
            String city = cityComboBox.getSelectionModel().getSelectedItem().toString();
            String zip = zipTxt.getText();

            if(!name.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !country.isEmpty()
                && !city.isEmpty() && !zip.isEmpty()) {
                DBQuery.saveCustomer(name, phone, address, country, city, zip);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setTitle("Missing customer info");
                alert.setHeaderText("Please fill out all information!");
                alert.showAndWait();
            }
        } catch(NullPointerException | SQLException e) {
            System.out.println("Error: " + e);
        }

    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        custIdTxt.setDisable(true);
        custIdTxt.setText("***Auto Generated***");

        countryComboBox.setItems(countries);
        countryComboBox.getSelectionModel().selectFirst();

        if(countryComboBox.getSelectionModel().getSelectedItem().equals("USA")) {
            cityComboBox.setItems(usaCities);
            cityComboBox.getSelectionModel().selectFirst();
        }

        countryComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue.equals("USA")) {
                cityComboBox.setItems(usaCities);
                cityComboBox.getSelectionModel().selectFirst();
            } else if (newValue.equals("UK")) {
                cityComboBox.setItems(ukCities);
                cityComboBox.getSelectionModel().selectFirst();
            } else if (newValue.equals("Canada")) {
                cityComboBox.setItems(canadaCities);
                cityComboBox.getSelectionModel().selectFirst();
            }
        });

    }
}
