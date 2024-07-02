package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import suppliers.DataAccessLayer.DataBase;




public class SupplierCategoriesDAO {
    public class DataTypeSupplierCategories {
        public int categoryId;
        public int supplierId;
        public DataTypeSupplierCategories(int categoryId, int supplierId) {
            this.categoryId = categoryId;
            this.supplierId = supplierId;
        }
    }
    private final String colCategoryId = "CategoryId";
    private final String colSupplierId = "SupplierId";
    private final String tableName = "SupplierCategories";

    private Connection conn = null;
    public SupplierCategoriesDAO() {
        this.conn = DataBase.getConnection();
    }
    
    public void addSupplierCategory(int supplierId, int categoryId) throws  SQLException{
        Statement stmt = conn.createStatement();
        String query = "INSERT INTO " + tableName + " (" + colCategoryId + ", " + colSupplierId + ") VALUES (" + categoryId + ", " + supplierId + ")";
        stmt.executeUpdate(query);
    }

    public void addAllSupplierCategory(int supplierId, List<Integer> categories) throws  SQLException{
        Statement stmt = conn.createStatement();
        for (Integer categoryId:categories) {
            String query = "INSERT INTO " + tableName + " (" + colCategoryId + ", " + colSupplierId + ") VALUES (" + categoryId + ", " + supplierId + ")";
            stmt.executeUpdate(query);
        }
    }
    public void deleteSupplierCategory(int supplierId, int categoryId) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM " + tableName + " WHERE " + colCategoryId + " = " + categoryId + " AND " + colSupplierId + " = " + supplierId;
        stmt.executeUpdate(query);
    }
    public void deleteSupplierCategories(int supplierId) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        stmt.executeUpdate(query);
    }
    public void deleteCategorySuppliers(int categoryId) throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM " + tableName + " WHERE " + colCategoryId + " = " + categoryId;
        stmt.executeUpdate(query);
    }
    public int getCategoryByCategoryId(int id) throws SQLException {

        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM " + tableName + " WHERE " + colCategoryId + " = " + id;
        ResultSet res = stmt.executeQuery(query);
        if (res.next()) {
            return res.getInt(colSupplierId);
        }
        throw new SQLException("Category not found");
    }
    public List<Integer> getCategoryBySupplierId(int id) throws SQLException {
        List<Integer> categories = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = " + id;
        ResultSet res = stmt.executeQuery(query);
        if (!res.next()) {
            throw new SQLException("Category not found");
        }
        while (res.next()) {
            categories.add(res.getInt(colCategoryId));
        }
        return categories;
    }

    public List<DataTypeSupplierCategories> getAllCategories() throws SQLException {
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM " + tableName;
        ResultSet res =  stmt.executeQuery(query);
        List<DataTypeSupplierCategories> categories = new ArrayList<>();
        while (res.next()) {
            categories.add(new DataTypeSupplierCategories(res.getInt(colCategoryId), res.getInt(colSupplierId)));
        }
        return categories;
    }

    
}