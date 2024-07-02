package suppliers.DataAccessLayer.DAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import suppliers.DomainLayer.*;

public class OrderDAO {

    private Connection conn;
    private OrderDeliveryDayDAO orderDeliveryDaysDAO;
    private OrderItemDAO orderItemsDAO;

    public OrderDAO() throws SQLException {
        conn = DataBase.getConnection();
        orderDeliveryDaysDAO = new OrderDeliveryDayDAO();
        orderItemsDAO = new OrderItemDAO();
    }

    public HashMap<Integer, Order> getAllOrders() throws SQLException, ParseException {
        HashMap<Integer, Order> orders = new HashMap<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Orders");
        while (rs.next()) {
            Order order = createOrder(rs);
            orders.put(order.getOrderId(), order);
        }
        return orders;
    }

    public Order getOrderById(int id) throws SQLException, ParseException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Orders WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return createOrder(rs);
        } else {
            return null;
        }
    }

    public void addOrder(Order order) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Orders (id, SupplierId, creationDate, deliveryDate, isConst) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, order.getOrderId());
        stmt.setInt(2, order.getSupplierId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        stmt.setString(3, dateFormat.format(order.getCreationDate()));
        stmt.setString(4, dateFormat.format(order.getDeliveryDate()));
        if (order.getConstDeliveryDays().size() > 0) {
            stmt.setInt(5, 1);
            orderDeliveryDaysDAO.addOrderDeliveryDays(order.getOrderId(), order.getConstDeliveryDays());
        } else {
            stmt.setInt(5, 0);
        }
        orderItemsDAO.addOrderItems(order.getOrderId(), order.getSupplierId(), order.getItems());
        stmt.executeUpdate();
    }

    public void updateOrder(Order order) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE Orders SET SupplierId = ?, creationDate = ?, deliveryDate = ?, isConst = ? WHERE id = ?");
        stmt.setInt(1, order.getSupplierId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        stmt.setString(2, dateFormat.format(order.getCreationDate()));
        stmt.setString(3, dateFormat.format(order.getDeliveryDate()));
        if (order.getConstDeliveryDays().size() > 0) {
            stmt.setInt(4, 1);
        } else {
            stmt.setInt(4, 0);
        }
        stmt.setInt(5, order.getOrderId());
        stmt.executeUpdate();
    }

    public void deleteOrder(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Orders WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        orderDeliveryDaysDAO.deleteOrderDeliveryDays(id);
        orderItemsDAO.deleteOrderItems(id);
    }

    private Order createOrder(ResultSet rs) throws SQLException, ParseException {
        int orderId = rs.getInt("id");
        int supplierId = rs.getInt("SupplierId");
        Supplier supplier = new SuppliersDAO().getSupplierById(supplierId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String cday = rs.getString("creationDate");
        String dday = rs.getString("deliveryDate");
        Date creationDate = dateFormat.parse(rs.getString("creationDate"));
        Date deliveryDate = dateFormat.parse(rs.getString("deliveryDate"));
        HashMap<Product, Integer> items = orderItemsDAO.getAllOrderItems(orderId);
        List<DaysOfTheWeek.Day> constDeliveryDays = new ArrayList<>();
        if (rs.getInt("isConst") == 1) {
            constDeliveryDays = orderDeliveryDaysDAO.getAllOrderDeliveryDaysByOrder(orderId);
        } else {
            constDeliveryDays = new ArrayList<DaysOfTheWeek.Day>();
        }
        return new Order(orderId, supplier, creationDate, deliveryDate, items, constDeliveryDays);
    }
}