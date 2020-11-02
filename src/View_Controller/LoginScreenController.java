package View_Controller;

import static Database.DBQuery.login;

import Database.DBQuery;
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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TextField usernameTxt;
    @FXML private PasswordField passwordTxt;

    @FXML private DialogPane appointmentSchedulerDP;
    @FXML private DialogPane usernameDP;
    @FXML private DialogPane passwordDP;
    @FXML private Button clearBtn;
    @FXML private Button loginBtn;

    String errorTitle;
    String errorHeader;

    @FXML void onActionClear(ActionEvent event) {
        usernameTxt.clear();
        passwordTxt.clear();
    }

    @FXML void onActionLogin(ActionEvent event) throws IOException {
        String usernameEntry = usernameTxt.getText();
        String passwordEntry = passwordTxt.getText();

        if (login(usernameEntry, passwordEntry)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            DBQuery.apptFifteenNotification();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.showAndWait();
        }

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        try {
            rb = ResourceBundle.getBundle("Languages/Nat", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("es")) {
                appointmentSchedulerDP.setContentText(rb.getString("appointmentScheduler"));
                usernameDP.setContentText(rb.getString("username"));
                passwordDP.setContentText(rb.getString("password"));
                clearBtn.setText(rb.getString("clear"));
                loginBtn.setText(rb.getString("login"));
                errorTitle = rb.getString("errorTitle");
                errorHeader = rb.getString("errorHeader");
            }
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

