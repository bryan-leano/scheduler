package View_Controller;

import Database.DBConnection;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private ChoiceBox reportCB;
    @FXML private TextArea reportsTextArea;

    static ObservableList<String> reports = FXCollections.observableArrayList();

    @FXML void onActionGenerate(ActionEvent event) throws IOException, SQLException {

        reportsTextArea.clear();

        String reportOptionSelected = reportCB.getValue().toString();

        if (reportOptionSelected.equals("Appointment Types by Month")) {
            reportsTextArea.setText(firstReportApptTypesByMonth());
        } else if (reportOptionSelected.equals("Schedule for Consultant")) {
            reportsTextArea.setText(secondReportApptsByConsultant());
        } else if (reportOptionSelected.equals("List of Customer's Cities")) {
            reportsTextArea.setText(thirdReportCustomerCity());
        }
    }

    public String firstReportApptTypesByMonth() throws SQLException {
        Connection conn = DBConnection.startConnection();

        try {
            StringBuilder firstReportText = new StringBuilder();
            firstReportText.append("Month    | # of each Type  \n______________________________________________________________________\n");

            ResultSet rsApptTypeMonth = conn.createStatement().executeQuery(String.format("SELECT MONTHNAME(start) as Month, type, COUNT(*) as Amount " +
                    "FROM appointment GROUP BY MONTH, type ORDER BY Month DESC;"));
            while (rsApptTypeMonth.next()) {
                firstReportText.append(rsApptTypeMonth.getString("Month") + "          " + rsApptTypeMonth.getString("Amount") + "   " + rsApptTypeMonth.getString("type") + "\n");
            }

            return firstReportText.toString();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "Doesn't work";
        }
    }

    public String secondReportApptsByConsultant() throws SQLException {
        Connection conn = DBConnection.startConnection();

        try {
            StringBuilder secondReportText = new StringBuilder();
            secondReportText.append("Consultant Test's Schedule\n_______________________________________________________________________________________________\n" +
                    "Date                Customer       Title           Appointment Info\n_______________________________________________________________________________________________\n");

            ResultSet rsConsultantSchedule = conn.createStatement().executeQuery(String.format("SELECT DATE(start) date, start, end, customerName, title, description, type, location "
                    + "FROM appointment a INNER JOIN customer c ON a.customerId=c.customerId WHERE userId=1 ORDER BY start;"));

            while (rsConsultantSchedule.next()) {

                String date = rsConsultantSchedule.getString("date");
                String name = rsConsultantSchedule.getString("customerName");
                String title = rsConsultantSchedule.getString("title");
                String type = rsConsultantSchedule.getString("type");

                secondReportText.append(date + "     " + name + "     " + title + "     " + "     " +  type + "     " + "\n\n");
            }

            return secondReportText.toString();
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "Doesn't work";
        }
    }

    public String thirdReportCustomerCity() throws SQLException {
        Connection conn = DBConnection.startConnection();

        try {
            StringBuilder thirdReportText = new StringBuilder();
            thirdReportText.append("Customer    | City  \n______________________________________________________________________\n");

            ResultSet rsCustomerCity = conn.createStatement().executeQuery(String.format("SELECT customerName, city " +
                    "FROM customer c INNER JOIN address a ON c.addressId = a.addressId INNER JOIN city i ON a.cityId = i.cityId " +
                    "INNER JOIN country o ON i.countryId = o.countryId;"));

            while (rsCustomerCity.next()) {
                thirdReportText.append(rsCustomerCity.getString("customerName") + "          " + rsCustomerCity.getString("city") + "\n");
            }

            return thirdReportText.toString();
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "Doesn't work";
        }
    }

    @FXML void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onActionClear(ActionEvent event) throws IOException {
        reportsTextArea.clear();
    }

    public static ObservableList<String> getReports() {
        reports.removeAll(reports);
        reports.add("Appointment Types by Month");
        reports.add("Schedule for Consultant");
        reports.add("List of Customer's Cities");
        return reports;
    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        reportCB.setItems(getReports());

    }
}
