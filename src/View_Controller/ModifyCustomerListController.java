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

    /*
    @FXML private TableView<> customerTableView;
    @FXML private TableColumn<,> custIdCol;
    @FXML private TableColumn<,> custNameCol;
    @FXML private TableColumn<,> custAddressCol;
    @FXML private TableColumn<,> custPhoneCol;

     */


    @FXML void onActionModifyCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyCustomer.fxml"));
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

    }

}
