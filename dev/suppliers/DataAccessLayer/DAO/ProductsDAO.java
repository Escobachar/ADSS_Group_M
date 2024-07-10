package suppliers.DataAccessLayer.DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DomainLayer.*;

public class ProductsDAO {
    private CategoriesDAO categoriesDAO;
    private ProductsDiscountDAO productsDiscountDAO;

    public ProductsDAO() throws SQLException {
        categoriesDAO = new CategoriesDAO();
        productsDiscountDAO = new ProductsDiscountDAO();
    }

    public HashMap<Integer, Product> getCategoryProducts(int sid, int categoryId) throws SQLException {
        Connection conn = DataBase.getConnection();
        HashMap<Integer, Product> products = new HashMap<>();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT * FROM Products WHERE SupplierId = ? AND CategoryId = ?");
        stmt.setInt(1, sid);
        stmt.setInt(2, categoryId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Product product = createProduct(rs);
            products.put(product.getCatalogNumber(), product);
        }
        DataBase.closeConnection();
        return products;
    }

    public HashMap<Integer, Product> getAllProductsBySupplier(int sid) throws SQLException {
        Connection conn = DataBase.getConnection();
        HashMap<Integer, Product> products = new HashMap<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE SupplierId = ?");
        stmt.setInt(1, sid);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Product product = createProduct(rs);
            products.put(product.getCatalogNumber(), product);
        }
        DataBase.closeConnection();
        return products;
    }

    public void addProduct(int sid, Product product) throws SQLException {
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Products (SupplierId, catalogNum, CategoryId, name, price, ordersCount) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, sid);
            stmt.setInt(2, product.getCatalogNumber());
            stmt.setInt(3, product.getCategory().getCategoryId());
            stmt.setString(4, product.getName());
            stmt.setDouble(5, product.getPrice());
            stmt.setInt(6, product.getOrdersCount());
            stmt.executeUpdate();
            DataBase.closeConnection();
            productsDiscountDAO.addDiscountQuantity(sid, product.getCatalogNumber(), product.getDiscount());
        }catch (Exception e)
        {
            System.out.println("addProduct "+ e.getMessage());
        }
    }

    public void insertAllProducts(int id, List<Product> products) throws SQLException {
        for (Product product: products ) {
            addProduct(id, product);
        }
    }

    public void updateProduct(int sid, Product product) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE Products SET CategoryId = ?, name = ?, price = ?, ordersCount = ? WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, product.getCategory().getCategoryId());
        stmt.setString(2, product.getName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getOrdersCount());
        stmt.setInt(5, sid);
        stmt.setInt(6, product.getCatalogNumber());
        stmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void deleteProduct(int sid, int catalogNumber) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Products WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, sid);
        stmt.setInt(2, catalogNumber);
        stmt.executeUpdate();
        DataBase.closeConnection();
        productsDiscountDAO.deleteDiscountQuantity(sid, catalogNumber);
    }

    public void deleteAllProductBySupplier(int sid) throws SQLException {
        Connection conn = DataBase.getConnection();
        HashMap<Integer, Product> productHashMap = getAllProductsBySupplier(sid);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Products WHERE SupplierId = ?");
        stmt.setInt(1, sid);
        stmt.executeUpdate();
        DataBase.closeConnection();
        for (Integer catalogNum : productHashMap.keySet()) {
            productsDiscountDAO.deleteDiscountQuantity(sid, catalogNum);
        }
    }

    public Product getProduct(int sid, int catalogNum) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT * FROM Products WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, sid);
        stmt.setInt(2, catalogNum);
        ResultSet rs = stmt.executeQuery();
        
        Product prd =  createProduct(rs);
        DataBase.closeConnection();
        return prd;
    }

    private Product createProduct(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int catalogNumber = rs.getInt("catalogNum");
        double price = rs.getDouble("price");
        int categoryId = rs.getInt("CategoryId");
        Category category = categoriesDAO.getCategoryById(categoryId);
        DiscountQuantity dq = productsDiscountDAO.getDiscountQuantity(rs.getInt("SupplierId"),
                rs.getInt("catalogNum"));
        Product product = new Product(name, catalogNumber, price, category, dq);
        product.setOrdersCount(rs.getInt("ordersCount"));
        return product;
    }

}