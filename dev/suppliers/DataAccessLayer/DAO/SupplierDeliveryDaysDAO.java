package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import suppliers.DaysOfTheWeek.Day;

import static suppliers.DaysOfTheWeek.DayToInt;


public class SupplierDeliveryDaysDAO {
    public class DataTypeSupplierDeliveryDays {
        public int supplierId;
        public int deliveryDay;
        public DataTypeSupplierDeliveryDays(int supplierId, int deliveryDay) {
            this.supplierId = supplierId;
            this.deliveryDay = deliveryDay;
        }
    }

    private final String colSupplierId = "SupplierId";
    private final String colDeliveryDay = "Day";
    private final String tableName = "SupplierDeliveryDays";
    
    private Connection conn = null;

    public SupplierDeliveryDaysDAO() {
        this.conn = DataBase.getConnection();
    }

    public void insert(int supplierId, Day deliveryDay) throws SQLException {
        int day = DayToInt(deliveryDay);
        String query = "INSERT INTO " + tableName + " VALUES (" + supplierId + ", " +  day + ")";
        conn.createStatement().executeUpdate(query);
    }
    
    public void insertAll(int supplierId, Day[] deliveryDays) throws SQLException {
        for (DaysOfTheWeek.Day deliveryDay : deliveryDays) {
            insert(supplierId, deliveryDay);
        }
    }

    public void delete(int supplierId, int deliveryDay) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId + " AND " + colDeliveryDay + " = " + deliveryDay;
        conn.createStatement().executeUpdate(query);
    }

    public void deleteAll(int supplierId) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        conn.createStatement().executeUpdate(query);
    }
    
    public List<Day> select(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        ResultSet result = conn.createStatement().executeQuery(query);
        List<Day> days = new ArrayList<>();
        while (result.next()) {
            days.add(IntToDay(result.getInt(colDeliveryDay)));
        }
        return days;
    }



    
}