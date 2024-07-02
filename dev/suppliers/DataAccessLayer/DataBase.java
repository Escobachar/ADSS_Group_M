package suppliers.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBase {
    private static DataBase dataBase = null;
    private static final String url = "jdbc:sqlite:C:\\\\Users\\97254\\ADSS_Group_M_2\\Suppliers.db";

    public Connection conn = null;
    private DataBase() {
        connectToDatabase();
    }

    public static Connection getConnection()  {
        try{
            return DriverManager.getConnection(url);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public  void connectToDatabase() {
        try {
            // db parameters
            String url = "jdbc:sqlite:C:\\\\Users\\97254\\ADSS_Group_M_2\\Suppliers.db";
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            System.out.println(conn.getMetaData().getURL());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}