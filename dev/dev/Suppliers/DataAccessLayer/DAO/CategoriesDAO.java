package Suppliers.DataAccessLayer.DAO;

import Suppliers.DataAccessLayer.DataBase;
import Suppliers.DomainLayer.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CategoriesDAO {
    private final String colNmae = "name";
    private final String colId = "id";
    private final String tableName = "Categories";
    public CategoriesDAO() {
    }
    public HashMap<String, Integer> getAllCategories() throws SQLException {
        Connection conn = DataBase.getConnection();
        HashMap<String, Integer> categories = new HashMap<>();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM " + tableName;
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                Category category = new Category(res.getString(colNmae), res.getInt(colId));
                categories.put(category.getCategoryName(), category.getCategoryId());
            }
        
        return categories;
    }
    public void addCategory(Category category) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (" + colNmae + ", " + colId + ") VALUES (?, ?)");
        stmt.setString(1, category.getCategoryName());
        stmt.setInt(2, category.getCategoryId());
        stmt.executeUpdate();
        
    }
    public void deleteCategory(Category category) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + colId + " = ?");
        stmt.setInt(1,category.getCategoryId());
        stmt.executeUpdate();
        
    }
    public Category getCategoryById(int id) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + colId + " = ?");
        stmt.setInt(1, id);
        ResultSet res = stmt.executeQuery();
        if (res.next()) {
            return new Category(res.getString(colNmae), res.getInt(colId));
        }
        
        return null;
    }
    public Category getCategoryByName(String name) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + colNmae + " = ?");
        stmt.setString(1, name);
        ResultSet res = stmt.executeQuery();
        if (res.next()) {
            return new Category(res.getString(colNmae), res.getInt(colId));
        }
        
        return null;
    }
}