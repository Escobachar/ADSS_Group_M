package Suppliers.DataAccessLayer.DAO;

import java.sql.*;
import java.util.HashMap;

import Suppliers.DataAccessLayer.DataBase;
import Suppliers.DomainLayer.Product;

public class OrderItemDAO {


    private String tableName = "OrderItems";
    private String orderIdColumnName = "OrderId";
    private String catalogNumColumnName = "catalogNum";
    private String supplierIdColumnName = "SupplierId";

    private String amountColumnName = "amount";
    private ProductsDAO productsDAO;


    public OrderItemDAO() throws SQLException {
        productsDAO = new ProductsDAO();
    }

    public HashMap<Product, Integer> getAllOrderItems(int orderId) throws SQLException {
        Connection conn = DataBase.getConnection();
        HashMap<Product, Integer> items = new HashMap<Product, Integer>();
        PreparedStatement stmt = conn.prepareStatement("SELECT "+ catalogNumColumnName+", "+supplierIdColumnName+", "+amountColumnName+" FROM "+tableName+" WHERE OrderId = ?");
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();
        

        while (rs.next()) {
            int catalogNum = rs.getInt(catalogNumColumnName);
            int supplierId = rs.getInt(supplierIdColumnName);
            int amount = rs.getInt(amountColumnName);
            
            Product product = productsDAO.getProduct(supplierId,catalogNum);
            items.put(product, amount);
        }
        
        return items;
    }

    public void addOrderItems(int orderId, int supId, HashMap<Product, Integer> items) throws SQLException {
        Connection conn = DataBase.getConnection();
        for (HashMap.Entry<Product, Integer> entry : items.entrySet()) {
            int catalogNum = entry.getKey().getCatalogNumber();
            int amount = entry.getValue();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO " + tableName + " (OrderId, catalogNum, SupplierId, amount)  VALUES (?, ?, ?, ?");
            stmt.setInt(1, orderId);
            stmt.setInt(2, catalogNum);
            stmt.setInt(3, supId);
            stmt.setInt(4, amount);
            stmt.executeUpdate();
            
        }
    }

    public void addOrderItem(int orderId, int supId, Product product, Integer amount) throws SQLException {
        Connection conn = DataBase.getConnection();
        int catalogNum = product.getCatalogNumber();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO " + tableName + " (OrderId, catalogNum, SupplierId, amount)  VALUES (?, ?, ?, ?");
        stmt.setInt(1, orderId);
        stmt.setInt(2, catalogNum);
        stmt.setInt(3, supId);
        stmt.setInt(4, amount);
        stmt.executeUpdate();
        
    }

    public void deleteOrderItems(int orderId) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("DELETE FROM " + tableName + " WHERE " + orderIdColumnName + " = ?");
        stmt.setInt(1, orderId);
        stmt.executeUpdate();
        
    }

    public void deleteOrderItems(int orderId, Product product) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + orderIdColumnName + " = ? AND " + catalogNumColumnName + " = ?");
        stmt.setInt(1, orderId);
        stmt.setInt(2, product.getCatalogNumber());
        stmt.executeUpdate();
        
    }

    public void updateProductAmount(int orderId, int catalogNum, int amount) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("UPDATE " + tableName + " SET " + amountColumnName + " = ? WHERE "
                        + orderIdColumnName + " = ? AND " + catalogNumColumnName + " = ?");
        stmt.setInt(1, amount);
        stmt.setInt(2, orderId);
        stmt.setInt(3, catalogNum);
        stmt.executeUpdate();
        
    }

}