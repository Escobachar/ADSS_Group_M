package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import suppliers.DaysOfTheWeek.Day;
import static suppliers.DaysOfTheWeek.DayToInt;
import static suppliers.DaysOfTheWeek.intToDay;


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
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?)");
        stmt.setInt(1, supplierId);
        stmt.setInt(2, day);
        stmt.executeUpdate();
    }
    
    public void insertAll(int supplierId, List<Day> deliveryDays) throws SQLException {
        for (DaysOfTheWeek.Day deliveryDay : deliveryDays) {
            insert(supplierId, deliveryDay);
        }
    }

    public void updateAll(int supplierId, List<Integer> deliveryDays) throws SQLException{
        deleteAll(supplierId);
        for (Integer deliveryDay : deliveryDays) {
            insert(supplierId, intToDay(deliveryDay));
        }    }

    public void delete(int supplierId, int deliveryDay) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + colSupplierId + " = ? AND " + colDeliveryDay + " = ?");
        stmt.setInt(1, supplierId);
        stmt.setInt(2, deliveryDay);
        stmt.executeUpdate();
    }

    public void deleteAll(int supplierId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + colSupplierId + " = ?");
        stmt.setInt(1, supplierId);
        stmt.executeUpdate();
    }
    
    public List<Day> select(int supplierId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = ?");
        stmt.setInt(1, supplierId);
        ResultSet result = stmt.executeQuery();
        List<Day> days = new ArrayList<>();
        while (result.next()) {
            days.add(DaysOfTheWeek.intToDay(result.getInt(colDeliveryDay)));
        }
        return days;
    }



    
}