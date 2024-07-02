package suppliers.DataAccessLayer.DAO;
import java.sql.*;
import java.util.HashMap;
import suppliers.DomainLayer.*;

public class ProductsDAO implements ProductsDAO {
    private Connection conn;
    private CategoriesDAO categoriesDAO;
    private ProductsDiscountDAO productsDiscountDAO;

    public ProductsDAO() throws SQLException {
        conn = Database.connect();
        categoriesDAO = new categoriesDAO();
        productsDiscountDAO = new ProductsDiscountDAO();
    }

    public HashMap<Integer, Product> getCategoryProducts(int sid, int categoryId) throws SQLException{
        HashMap<Integer, Product> products = new HashMap<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE SupplierId = ? AND CategoryId = ?");
        stmt.setInt(1, sid); 
        stmt.setInt(2, categoryId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Product product = createProduct(rs);
            products.put(product.getCatalogNumber(), product);
        }
        return products;
    }

    public void addProduct(int sid, Product product) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Products (SupplierId, catalogNum, CategoryId, name, price, ordersCount) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, sid);
        stmt.setInt(2, product.getCatalogNumber());
        stmt.setInt(3, product.getCategory().getCategoryId());
        stmt.setString(4, product.getName());
        stmt.setDouble(5, product.getPrice());
        stmt.setInt(6, product.getOrdersCount());
        stmt.executeUpdate();
        productsDiscountDAO.addDiscountQuantity(sid, product.getCatalogNumber(), product.getDiscount());
    }

    public void updateProduct(int sid, Product product) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Products SET CategoryId = ?, name = ?, price = ?, ordersCount = ? WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, product.getCategory().getCategoryId());
        stmt.setString(2, product.getName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getOrdersCount());
        stmt.setInt(5, sid);
        stmt.setInt(6, product.getCatalogNumber());
        stmt.executeUpdate();
        }

    public void deleteProduct(int sid, int catalogNumber) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Products WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, sid);
        stmt.setInt(2, catalogNumber);
        stmt.executeUpdate();
        productsDiscountDAO.deleteDiscountQuantity(sid, catalogNumber);
    }

    private Product createProduct(ResultSet rs) throws SQLException{
        String name = rs.getString("name");
        int catalogNumber = rs.getInt("catalogNum");
        double price = rs.getDouble("price");
        int categoryId = rs.getInt("CategoryId");
        Category category = categoriesDAO.getCategoryById(categoryId);
        DiscountQuantity dq = productsDiscountDAO.getDiscountQuantity(rs.getInt("SupplierId"), rs.getInt("catalogNumber"));
        Product product = new Product(name, catalogNumber, price, category, dq);
        product.setOrdersCount(rs.getInt("ordersCount"));
        return product;
    }

}