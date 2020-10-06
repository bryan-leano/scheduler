package sample;

import Database.DBConnection;
import Database.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/LoginScreen.fxml"));
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws  SQLException {

        Connection conn = DBConnection.startConnection();
        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();

        String country, createDate, createdBy, lastUpdateBy;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a country: ");

        country = keyboard.nextLine();
        createDate = "2020-10-05 00:00:00";
        createdBy = "admin";
        lastUpdateBy = "admin";

        if(country.contains("'")){
            country = country.replace("'", "\\'");
        }

        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy)" +
                "VALUES(" +
                "'" + country + "'," +
                "'" + createDate + "'," +
                "'" + createdBy + "'," +
                "'" + lastUpdateBy + "'" +
                ")";

        try{
            statement.execute(insertStatement);

            if(statement.getUpdateCount() > 0) {
                System.out.println(statement.getUpdateCount() + " row(s) affected!");
            } else {
                System.out.println("No change!");
            }

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        launch(args);
        DBConnection.closeConnection();
    }
}
