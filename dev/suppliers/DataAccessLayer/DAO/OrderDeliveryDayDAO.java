package suppliers.DataAccessLayer.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import static suppliers.DaysOfTheWeek.DayToInt;
import static suppliers.DaysOfTheWeek.intToDay;

public class OrderDeliveryDayDAO {
    private String tableName = "OrderDeliveryDays";
    private String orderIdColumnName = "OrderId";
    private String dayColumnName = "day";


    public OrderDeliveryDayDAO() throws SQLException{
    }

    public List<DaysOfTheWeek.Day> getAllOrderDeliveryDaysByOrder(int orderId) throws SQLException {
        Connection conn = DataBase.getConnection();
        List<DaysOfTheWeek.Day> days = new ArrayList<DaysOfTheWeek.Day>();
        PreparedStatement stmt = conn.prepareStatement("SELECT "+ dayColumnName+" FROM "+tableName+" WHERE OrderId = ?");
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            DaysOfTheWeek.Day day = intToDay(rs.getInt(dayColumnName));
            days.add(day);
        }
        
        return days;
    }
    public void addOrderDeliveryDay(int orderId, DaysOfTheWeek.Day day) throws SQLException {
        Connection conn = DataBase.getConnection();
        int intDay = DayToInt(day);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO "+ tableName+" (OrderId, day) VALUES (?, ?)");
        stmt.setInt(1, orderId);
        stmt.setInt(2, intDay);
        stmt.executeUpdate();
        
    }

    public void addOrderDeliveryDays(int orderId, List<DaysOfTheWeek.Day> days) throws SQLException {
        Connection conn = DataBase.getConnection();
        for (DaysOfTheWeek.Day day : days) {
            int intDay = DayToInt(day);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (OrderId, day) VALUES (?, ?)");
            stmt.setInt(1, orderId);
            stmt.setInt(2, intDay);
            stmt.executeUpdate();
            
        }
    }
    public void deleteOrderDeliveryDay(int orderId, DaysOfTheWeek.Day day) throws SQLException {
        Connection conn = DataBase.getConnection();
        int intDay = DayToInt(day);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM "+ tableName+" WHERE "+orderIdColumnName +" = ? AND "+ dayColumnName+" = ?");
        stmt.setInt(1, orderId);
        stmt.setInt(2, intDay);
        stmt.executeUpdate();
        
    }
    public void deleteOrderDeliveryDays(int orderId) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM "+ tableName+" WHERE "+orderIdColumnName +" = ?");
        stmt.setInt(1, orderId);
        stmt.executeUpdate();
        
    }
    }