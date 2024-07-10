package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    }

    public void addSupplierCategory(int supplierId, int categoryId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO " + tableName + " (" + colCategoryId + ", " + colSupplierId + ") VALUES (?, ?)");
        // String query = "INSERT INTO " + tableName + " (" + colCategoryId + ", " +
        // colSupplierId + ") VALUES (" + categoryId + ", " + supplierId + ")";
        stmt.setInt(1, categoryId);
        stmt.setInt(2, supplierId);
        stmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void addAllSupplierCategory(int supplierId, List<Integer> categories) throws SQLException {
        this.conn = DataBase.getConnection();
        for (Integer categoryId : categories) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO " + tableName + " (" + colCategoryId + ", " + colSupplierId + ") VALUES (?, ?)");
            stmt.setInt(1, categoryId);
            stmt.setInt(2, supplierId);
            stmt.executeUpdate();
        }
        DataBase.closeConnection();
    }

    public void deleteSupplierCategory(int supplierId, int categoryId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM " + tableName + " WHERE " + colCategoryId + " = ? AND " + colSupplierId + " = ?");
        stmt.setInt(1, categoryId);
        stmt.setInt(2, supplierId);
        stmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void deleteSupplierCategories(int supplierId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + colSupplierId + " = ?");
        stmt.setInt(1, supplierId);
        stmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void deleteCategorySuppliers(int categoryId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + colCategoryId + " = ?");
        stmt.setInt(1, categoryId);
        stmt.executeUpdate();
        DataBase.closeConnection();
    }

    public Integer getCategoryByCategoryId(int id) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT * FROM " + tableName + " WHERE " + colCategoryId + " = ?");
        stmt.setInt(1, id);
        ResultSet res = stmt.executeQuery();
        if (res.next()) {
            int sid = res.getInt(colSupplierId);
            DataBase.closeConnection();
            return id;
        }
        DataBase.closeConnection();
        return null;
    }

    public List<Integer> getCategoryBySupplierId(int id) throws SQLException {
        this.conn = DataBase.getConnection();
        List<Integer> categories = new ArrayList<>();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = ?");
        stmt.setInt(1, id);
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            categories.add(res.getInt(colCategoryId));
        }
        DataBase.closeConnection();
        return categories;
    }

    public List<DataTypeSupplierCategories> getAllCategories() throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName);
        ResultSet res = stmt.executeQuery();
        List<DataTypeSupplierCategories> categories = new ArrayList<>();
        while (res.next()) {
            categories.add(new DataTypeSupplierCategories(res.getInt(colCategoryId), res.getInt(colSupplierId)));
        }
        DataBase.closeConnection();
        return categories;
    }

}