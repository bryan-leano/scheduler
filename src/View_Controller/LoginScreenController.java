package View_Controller;

import static Database.DBQuery.login;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController {

    Stage stage;
    Parent scene;

    private String errorTitle;
    private String errorHeaderMissing;
    private String errorHeaderIncorrect;
    private String errorContentMissing;
    private String errorContentIncorrect;

    @FXML private TextField usernameTxt;
    @FXML private PasswordField passwordTxt;

    @FXML void onActionClear(ActionEvent event) {
        usernameTxt.clear();
        passwordTxt.clear();
    }

    @FXML void onActionLogin(ActionEvent event) throws IOException {
        String usernameEntry = usernameTxt.getText();
        String passwordEntry = passwordTxt.getText();

        if (login(usernameEntry, passwordEntry)) {
            System.out.println("Login works! Going to Main Screen");
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Invalid login credentials");
            alert.setHeaderText("Username or password is incorrect!");
            alert.showAndWait();
        }

    }
}

