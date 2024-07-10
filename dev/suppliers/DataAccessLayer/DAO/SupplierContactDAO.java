package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import suppliers.DataAccessLayer.DataBase;

public class SupplierContactDAO {
    public class DataTypeSupplierContact {
        public int supplierId;
        public String contactName;
        public String contactNum;

        public DataTypeSupplierContact(int supplierId, String contactName, String contactNum) {
            this.supplierId = supplierId;
            this.contactName = contactName;
            this.contactNum = contactNum;
        }
    }

    private final String colSupplierId = "SupplierId";
    private final String colContactName = "contactName";
    private final String colContactNum = "contactNum";
    private final String tableName = "SupplierContacts";

    private Connection conn = null;

    public SupplierContactDAO() {
    }

    public void insert(int supplierId, String cName, String cNum) throws SQLException {
        this.conn = DataBase.getConnection();
        String query = "INSERT INTO SupplierContacts (SupplierId, contactName, contactNum) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, supplierId);
        pstmt.setString(2, cName);
        pstmt.setString(3, cNum);
        pstmt.executeUpdate();
        DataBase.closeConnection();

    }

    public void insertAll(int supplierId, HashMap<String, String> contacts) throws SQLException {
        this.conn = DataBase.getConnection();
        for (Map.Entry<String, String> contact : contacts.entrySet()) {
            String query = "INSERT INTO " + tableName + " (" + colSupplierId + ", " + colContactName + ","
                    + colContactNum + ") VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            conn.createStatement().executeUpdate(query);
        }
        DataBase.closeConnection();
    }

    public void update(int supplierId, String contactName, String contactNum) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE " + tableName + " SET " + colContactName + " = ?, "
                + colContactNum + " = ? WHERE " + colSupplierId + " = ?");
        pstmt.setString(1, contactName);
        pstmt.setString(2, contactNum);
        pstmt.setInt(3, supplierId);
        pstmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void updateAll(int supplierId, HashMap<String, String> contacts) throws SQLException {
        deleteAll(supplierId);
        for (Map.Entry<String, String> contact : contacts.entrySet()) {
            insert(supplierId, contact.getKey(), contact.getValue());
        }
    }

    public void deleteAll(int supplierId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement pstmt = conn
                .prepareStatement("DELETE FROM " + tableName + " WHERE " + colSupplierId + " = ?");
        pstmt.setInt(1, supplierId);
        pstmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void delete(int supplierId, String contactName) throws SQLException {
        this.conn = DataBase.getConnection();
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = ? AND " + colContactName + " = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, supplierId);
        pstmt.setString(2, contactName);
        pstmt.executeUpdate();
        DataBase.closeConnection();
    }

    public List<DataTypeSupplierContact> select(int supplierId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn
                .prepareStatement("SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = ?");
        stmt.setInt(1, supplierId);
        var result = stmt.executeQuery();
        
        List<DataTypeSupplierContact> list = new ArrayList<>();
        while (result.next()) {
            list.add(new DataTypeSupplierContact(result.getInt(colSupplierId), result.getString(colContactName),
                    result.getString(colContactNum)));
        }
        DataBase.closeConnection();
        return list;
    }

    public List<DataTypeSupplierContact> selectAll() throws SQLException {
        this.conn = DataBase.getConnection();
        String query = "SELECT * FROM " + tableName;
        var result = conn.createStatement().executeQuery(query);
        List<DataTypeSupplierContact> list = new ArrayList<>();
        while (result.next()) {
            list.add(new DataTypeSupplierContact(result.getInt(colSupplierId), result.getString(colContactName),
                    result.getString(colContactNum)));
        }
        DataBase.closeConnection();
        return list;
    }

}