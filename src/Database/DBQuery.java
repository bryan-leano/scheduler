package Database;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class DBQuery {

    private static PreparedStatement statement;

    public static ObservableList<String> times = FXCollections.observableArrayList();


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

    ////CUSTOMER FUNCTIONS////

    public static void saveCustomer(String name, String phone, String address, String country,
                                    String city, String zip) throws SQLException {

        Connection conn = DBConnection.startConnection();

        Statement stmtGetCityId = conn.createStatement();
        ResultSet rsCityId = stmtGetCityId.executeQuery(String.format("SELECT cityId FROM city " +
                "WHERE + city = '%s'", city));
        rsCityId.next();

        Statement stmtAddAddress = conn.createStatement();
        stmtAddAddress.executeUpdate(String.format("INSERT INTO address (address, address2, " +
                "cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES ('%s', 'N/A', %s, '%s', '%s', NOW(), 'admin', " +
                "NOW(), 'admin')", address, rsCityId.getString("cityId"), zip, phone));

        Statement stmtGetAddressId = conn.createStatement();
        ResultSet rsAddressId = stmtGetAddressId.executeQuery(String.format("SELECT addressId FROM " +
                "address WHERE address = '%s' AND cityId = '%s' AND postalCode = '%s' " +
                "AND phone = '%s'", address, rsCityId.getString("cityId"), zip, phone));
        rsAddressId.next();

        Statement stmtAddCustomer = conn.createStatement();
        stmtAddCustomer.executeUpdate(String.format("INSERT INTO customer (customerName, " +
                "addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES ('%s', '%s', 1, NOW(), 'admin', NOW(), 'admin')",
                name, rsAddressId.getString("addressId")));
    }

    public static void modifyCustomer(String id, String name, String phone, String address, String country,
                                      String city, String zip) throws SQLException {
        try {
            Connection conn = DBConnection.startConnection();

            conn.createStatement().executeUpdate(String.format("UPDATE customer"
                            + " SET customerName='%s', lastUpdate='%s', lastUpdateBy='admin'"
                            + " WHERE customerId='%s'",
                    name, LocalDateTime.now(), id));

            ResultSet rsCityId = conn.createStatement().executeQuery(
                    String.format("SELECT cityId FROM city WHERE city = '%s'", city));
            rsCityId.next();
            ResultSet rsAddressId = conn.createStatement().executeQuery(
                    String.format("SELECT addressId FROM customer WHERE customerId = '%s'", id));
            rsAddressId.next();

            conn.createStatement().executeUpdate(String.format("UPDATE address"
                            + " SET address='%s', cityId='%s', postalCode='%s', " +
                            "phone='%s', lastUpdate='%s', lastUpdateBy='admin' WHERE addressId='%s'",
                    address, rsCityId.getString("cityId"), zip, phone,
                    LocalDateTime.now(), rsAddressId.getString("addressId")));
            System.out.println("This works");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void deleteCustomer(int id) {

        try {
            Connection conn = DBConnection.startConnection();

            ResultSet rsAddressId = conn.createStatement().executeQuery(String.format("SELECT " +
                    "addressId FROM customer WHERE customerId='%s'", id));
            rsAddressId.next();

            conn.createStatement().executeUpdate(String.format("DELETE FROM appointment"
                    + " WHERE customerId='%s'", id));

            conn.createStatement().executeUpdate(String.format("DELETE FROM customer"
                    + " WHERE customerId='%s'", id));

            conn.createStatement().executeUpdate(String.format("DELETE FROM address"
                    + " WHERE addressId='%s'", rsAddressId.getString("addressId")));

        } catch(SQLException e) {
            System.out.println(" Error while deleting: " + e);
        }

    }

    ////APPOINTMENT FUNCTIONS////

    public static void saveAppointment(String name, String id, String title, String type, String date,
                                       String startTime, String endTime) throws SQLException {

        LocalDateTime localStart = varLDT_UTC(startTime, date);
        LocalDateTime localEnd = varLDT_UTC(endTime, date);
        String UTCstart = localStart.toString();
        String UTCend = localEnd.toString();

        Connection conn = DBConnection.startConnection();
        Statement stmtAddAppt = conn.createStatement();

        stmtAddAppt.executeUpdate(String.format("INSERT INTO appointment (customerId, " +
                        "userId, title, description, location, contact, type, url, start, end, " +
                        "createDate, createdBy, lastUpdate, lastUpdateBy) VALUES ('%s', '%s', " +
                        "'%s', 'N/A', 'N/A', 'N/A', '%s', 'N/A', '%s', '%s', NOW(), 'admin', " +
                        "NOW(), 'admin')", id, 1, title, type, UTCstart, UTCend));
    }

    ////TIME BASED FUNCTIONS////

    public static LocalDateTime varLDT_UTC(String time, String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDT =  LocalDateTime.parse(date + " " + time, format).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        return localDT;
    }

    public static ObservableList<String> getTimes() {
        try {
            times.removeAll(times); //prevents cities from copying themselves to the list
            for (int i = 0; i < 24; i++ ) {
                String hour;
                if(i < 10) {
                    hour = "0" + i;
                }

                else {
                    hour = Integer.toString(i);
                }
                times.add(hour + ":00:00");
                times.add(hour + ":15:00");
                times.add(hour + ":30:00");
                times.add(hour + ":45:00");
            }
            times.add("24:00:00"); //add midnight to the list

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return times;
    }

}
