package View_Controller;

import Model.Appointment;
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

public class MainScreenController {

    Stage stage;
    Parent scene;

    @FXML private RadioButton weekRBtn;
    @FXML private RadioButton monthRBtn;
    @FXML private RadioButton viewAllRBtn;

    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, > apptIdCol;
    @FXML private TableColumn<Appointment, > customerCol;
    @FXML private TableColumn<Appointment, > titleCol;
    @FXML private TableColumn<Appointment, > typeCol;
    @FXML private TableColumn<Appointment, > dateCol;
    @FXML private TableColumn<Appointment, > startTimeCol;
    @FXML private TableColumn<Appointment, > endTimeCol;

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

    /*
    @FXML void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    };
    */

}
