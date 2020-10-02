package sample;

import Database.DBConnection;
import Database.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/LoginScreen.fxml"));
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        DBConnection.startConnection();

        launch(args);
        DBConnection.closeConnection();
    }
}
