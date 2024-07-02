package suppliers.DataAccessLayer.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import static suppliers.DaysOfTheWeek.DayToInt;
import static suppliers.DaysOfTheWeek.intToDay;

public class OrderDeliveryDayDAO {
    private Connection conn;
    private String tableName = "OrderDeliveryDays";
    private String orderIdColumnName = "OrderId";
    private String dayColumnName = "day";


    public OrderDeliveryDayDAO() throws SQLException{
        conn = DataBase.getConnection();
    }

    public List<DaysOfTheWeek.Day> getAllOrderDeliveryDaysByOrder(int orderId) throws SQLException {
        List<DaysOfTheWeek.Day> days = new ArrayList<DaysOfTheWeek.Day>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT "+ dayColumnName+" FROM "+tableName+" WHERE OrderId ="+orderId);
        while (rs.next()) {
            DaysOfTheWeek.Day day = intToDay(rs.getInt(dayColumnName));
            days.add(day);
        }
        return days;
    }
    public void addOrderDeliveryDay(int orderId, DaysOfTheWeek.Day day) throws SQLException {
        int intDay = DayToInt(day);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO "+ tableName+" "+orderId+" ,"+intDay);
        stmt.executeUpdate();
    }

    public void addOrderDeliveryDays(int orderId, List<DaysOfTheWeek.Day> days) throws SQLException {
        for (DaysOfTheWeek.Day day : days) {
            int intDay = DayToInt(day);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " " + orderId + " ," + intDay);
            stmt.executeUpdate();
        }
    }
    public void deleteOrderDeliveryDay(int orderId, DaysOfTheWeek.Day day) throws SQLException {
        int intDay = DayToInt(day);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM"+ tableName+" WHERE "+orderIdColumnName +"="+orderId+" AND "+ dayColumnName+"="+intDay);
        stmt.executeUpdate();
    }
    public void deleteOrderDeliveryDays(int orderId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM"+ tableName+" WHERE "+orderIdColumnName +"="+orderId);
        stmt.executeUpdate();
    }
    }