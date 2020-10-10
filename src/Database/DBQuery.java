package Database;
import Database.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.Optional;


public class DBQuery {

    private static PreparedStatement statement;

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

    ////LOGIN SCREEN FUNCTIONS////

    public static boolean login(String usernameEntry, String passwordEntry) {
        try {
            Connection conn = DBConnection.startConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE "
            + "username = ? AND password = ?");
            ps.setString(1, usernameEntry);
            ps.setString(2, passwordEntry);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    ////ADD CUSTOMER FUNCTIONS////

    public static void saveCustomer(String name, String phone, String address, String country,
                                    String city, String zip) throws SQLException {

        System.out.println(city);

        Connection conn = DBConnection.startConnection();

        //Get city id!!//
        /*
        String insertStatement = "INSERT INTO customer () VALUES ()";

        DBQuery.setPreparedStatement(conn, deleteStatement);

        PreparedStatement ps = DBQuery.getPreparedStatement();

         */

    }



}
