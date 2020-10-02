package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String IpAddress = "//3.227.166.251/U06P8d";

    private static final String jdbcURL = protocol + vendorName + IpAddress;

    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U06P8d";
    private static final String password = "53688828433";

    public static Connection startConnection()
    {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        return conn;
    }

    public static void closeConnection() {
        try{
            conn.close();
            System.out.println("Connection closed.");
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
