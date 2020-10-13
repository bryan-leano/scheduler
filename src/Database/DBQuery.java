package Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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

}
