package suppliers.DataAccessLayer.DAO;
import suppliers.DaysOfTheWeek;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static suppliers.DaysOfTheWeek.DayToInt;

public class OrderDeliveryDayDAO {
    private Connection conn;
    private String tableName = "OrderDeliveryDays";
    private String orderIdColumnName = "OrderId";
    private String dayColumnName = "day";


    public OrderDeliveryDayDAO() throws SQLException{
        conn = DataBase.connect();
    }

    public List<Integer> getAllOrderDeliveryDaysByOrder(int orderId) throws SQLException {
        List<Integer> days = new ArrayList<Integer>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT "+ dayColumnName+" FROM "+tableName+" WHERE id ="+orderId);
        while (rs.next()) {
            days.add(rs.getInt(dayColumnName));
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