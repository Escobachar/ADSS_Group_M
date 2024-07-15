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
       if(conn == null) {
        if(dataBase == null)
            dataBase = new DataBase();
           dataBase.connectToDatabase();
           return conn;
       }
        else{
            return conn;
        }
        
    }


    public  void connectToDatabase() {
        try {
            // db parameters
            String url = "jdbc:sqlite:Suppliers.db";
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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