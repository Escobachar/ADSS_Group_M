package suppliers.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBase {
    private static DataBase dataBase = null;
    public Connection conn = null;
    private DataBase() {
        connectToDatabase();
    }

    public static DataBase getConn() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }
    
    public  void connectToDatabase() {  
        try {  
            // db parameters  
            String url = "jdbc:sqlite:C:\\\\Users\\GoomeGum\\Desktop\\omer\\semester D\\n" + //
                                "itutz\\ADSS_Group_M\\test.db";  
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
