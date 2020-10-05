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

        String selectStatement = "SELECT * FROM country";
        statement.execute(selectStatement);
        ResultSet rs = statement.getResultSet();

        while(rs.next()) {
            int countryId = rs.getInt("countryId");
            String countryName = rs.getString("country");
            LocalDate date = rs.getDate("createDate").toLocalDate();
            LocalTime time = rs.getTime("createDate").toLocalTime();
            String createdBy = rs.getString("createdBy");
            LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();

            System.out.println(countryId + " | " + countryName + " | " + date + " | " + time
                    + " | " + createdBy + " | " + lastUpdate);
        }

        launch(args);
        DBConnection.closeConnection();
    }
}
