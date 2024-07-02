package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import suppliers.DataAccessLayer.DataBase;
import suppliers.DomainLayer.Category;

public class CategoriesDAO {
    private final String colNmae = "name";
    private final String colId = "id";
    private final String tableName = "categories";
    private Connection conn = null;
    public CategoriesDAO() {
        this.conn = DataBase.getConnection();
    }
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM " + tableName;
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                Category category = new Category(res.getString(colNmae), res.getInt(colId));
                categories.add(category);
            }
        return categories;
    }
    public void addCategory(Category category) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "INSERT INTO " + tableName + " (" + colNmae + ", " + colId + ") VALUES ('" + category.getCategoryName() + "', " + category.getCategoryId() + ")";
        stmt.executeUpdate(query);
    }
    public void deleteCategory(Category category) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM " + tableName + " WHERE " + colId + " = " + category.getCategoryId();
        stmt.executeUpdate(query);
    }
    public Category getCategoryById(int id) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM " + tableName + " WHERE " + colId + " = " + id;
        ResultSet res = stmt.executeQuery(query);
        if (res.next()) {
            return new Category(res.getString(colNmae), res.getInt(colId));
        }
        return null;
    }
    public Category getCategoryByName(String name) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM " + tableName + " WHERE " + colNmae + " = '" + name + "'";
        ResultSet res = stmt.executeQuery(query);
        if (res.next()) {
            return new Category(res.getString(colNmae), res.getInt(colId));
        }
        return null;
    }
}