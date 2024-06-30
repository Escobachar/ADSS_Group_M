package suppliers.DataAccessLayer.DAO;
import java.sql.*;

public class OrdersDAOImpl implements OrdersDAO{
    private Connection conn;
public OrdersDAOImpl() throws SQLException {
conn = Database.connect();
}

public List<Order> getAllOrders() {
    List<Order> orders = new ArrayList<Order>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Orders");
        while (rs.next()) {
            Order order = new Order();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            users.add(user);
        }
        return users;
    
}
}
