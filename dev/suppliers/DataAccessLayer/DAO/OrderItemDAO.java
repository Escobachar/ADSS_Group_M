package suppliers.DataAccessLayer.DAO;

import java.sql.*;
import java.util.HashMap;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DomainLayer.Product;

public class OrderItemDAO {

    private Connection conn;
    private String tableName = "OrderItems";
    private String orderIdColumnName = "OrderId";
    private String catalogNumColumnName = "catalogNum";
    private String supplierIdColumnName = "SupplierId";

    private String amountColumnName = "amount";

    public OrderItemDAO() throws SQLException {
        conn = DataBase.getConnection();
    }

    public HashMap<Product, Integer> getAllOrderItems(int orderId) throws SQLException {
        HashMap<Product, Integer> items = new HashMap<Product, Integer>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT "+ catalogNumColumnName+", "+supplierIdColumnName+", "+amountColumnName+" FROM "+tableName+" WHERE OrderId ="+orderId);
        while (rs.next()) {
            int catalogNum = rs.getInt(catalogNumColumnName);
            int supplierId = rs.getInt(supplierIdColumnName);
            int amount = rs.getInt(amountColumnName);

            Product product = new ProductsDAO().getProduct(supplierId, catalogNum);
            items.put(product, amount);
        }
        return items;
    }
    public void addOrderItems(int orderId,int supId, HashMap<Product, Integer> items) throws SQLException {

        for (HashMap.Entry<Product, Integer> entry : items.entrySet()) {
            int catalogNum = entry.getKey().getCatalogNumber();
            int amount = entry.getValue();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "+ tableName+" "+orderId+" ,"+catalogNum+" ,"+supId+" ,"+amount);
            stmt.executeUpdate();
        }
    }
    public void addOrderItem(int orderId,int supId, Product product, Integer amount) throws SQLException {
            int catalogNum = product.getCatalogNumber();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "+ tableName+" "+orderId+" ,"+catalogNum+" ,"+supId+" ,"+amount);
            stmt.executeUpdate();
    }

    public void deleteOrderItems(int orderId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM"+ tableName+" WHERE "+orderIdColumnName +"="+orderId);
        stmt.executeUpdate();
    }
    public void deleteOrderItems(int orderId, Product product) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM"+ tableName+" WHERE "+orderIdColumnName +"="+orderId+" AND "+ catalogNumColumnName+"="+product.getCatalogNumber());
        stmt.executeUpdate();
    }

    public void updateProductAmount(int orderId, int catalogNum, int amount) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE "+ tableName+" SET "+amountColumnName+" = "+amount+" WHERE "+orderIdColumnName +"="+orderId+" AND "+ catalogNumColumnName+"="+catalogNum);
        stmt.executeUpdate();
    }


}