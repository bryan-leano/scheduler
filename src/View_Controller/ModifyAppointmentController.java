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
import java.time.LocalDate;
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
        System.out.println("Fix Save Button Later");
    }

    @FXML void onActionSelectCust(ActionEvent event) throws IOException {
        System.out.println("Fix Select Button Later");
    }

    @FXML void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        apptIdTxt.setDisable(true);



    }
}
