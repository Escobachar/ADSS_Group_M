package suppliers.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBase {
    private static DataBase dataBase = null;
    private static final String url = "jdbc:sqlite:Suppliers.db";

    public static Connection conn = null;
    private DataBase() {
        connectToDatabase();
    }

    public static Connection getConnection()  {
        try{
            conn = DriverManager.getConnection(url);
            return conn;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void connectToDatabase() {
        try {
            // db parameters
            String url = "jdbc:sqlite:Suppliers.db";
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            System.out.println(conn.getMetaData().getURL());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAll() throws SQLException {
        getConnection();
        String query = "BEGIN;\n" +
                "\n" +
                "DELETE FROM Categories;\n" +
                "DELETE FROM OrderDeliveryDays;\n" +
                "DELETE FROM OrderItems;\n" +
                "DELETE FROM Orders;\n" +
                "DELETE FROM Products;\n" +
                "DELETE FROM ProductsDiscount;\n" +
                "DELETE FROM SupplierCategories;\n" +
                "DELETE FROM SupplierContacts;\n" +
                "DELETE FROM SupplierDeliveryDays;\n" +
                "DELETE FROM Suppliers;\n" +
                "\n" +
                "COMMIT;";
        conn.createStatement().executeUpdate(query);
    }
}