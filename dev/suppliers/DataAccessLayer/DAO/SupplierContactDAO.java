package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
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
    private final String tableName = "SupplierContact";

    private Connection conn = null;

    public SupplierContactDAO() {
        this.conn = DataBase.getConnection();
    }

    public void insert(int supplierId, String contactName, String contactNum) throws SQLException {
        String query = "INSERT INTO " + tableName + " VALUES (" + supplierId + ", " + contactName + ", " + contactNum
                + ")";
        conn.createStatement().executeUpdate(query);
    }

    public void insertAll(int supplierId, HashMap<String, String> contacts) throws SQLException {
        for (Map.Entry<String,String> contact:contacts.entrySet()) {
            String query = "INSERT INTO " + tableName + " VALUES (" + supplierId + ", " + contact.getKey() + ", " + contact.getValue()
                    + ")";
            conn.createStatement().executeUpdate(query);
        }
    }

    public void update(int supplierId, String contactName, String contactNum) throws SQLException {
        String query = "UPDATE " + tableName + " SET " + colContactName + " = " + contactName + ", " + colContactNum
                + " = " + contactNum + " WHERE " + colSupplierId + " = " + supplierId;
        conn.createStatement().executeUpdate(query);
    }

    public void updateAll(int supplierId, HashMap<String, String> contacts) throws SQLException {
        deleteAll(supplierId);
        for (Map.Entry<String,String> contact:contacts.entrySet()) {
            insert(supplierId, contact.getKey(), contact.getValue());
        }
    }

    public void deleteAll(int supplierId) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        conn.createStatement().executeUpdate(query);
    }

    public void delete(int supplierId, String contactName) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId + " AND "
                + colContactName + " = " + contactName;
        conn.createStatement().executeUpdate(query);
    }

    public List<DataTypeSupplierContact> select(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        var result = conn.createStatement().executeQuery(query);
        if (!result.next()) {
            throw new SQLException("No such supplier contact");
        }
        List<DataTypeSupplierContact> list = new ArrayList<>();
        while (result.next()) {
            list.add(new DataTypeSupplierContact(result.getInt(colSupplierId), result.getString(colContactName),
                    result.getString(colContactNum)));
        }
        return list;
    }

    public List<DataTypeSupplierContact> selectAll() throws SQLException {
        String query = "SELECT * FROM " + tableName;
        var result = conn.createStatement().executeQuery(query);
        List<DataTypeSupplierContact> list = new ArrayList<>();
        while (result.next()) {
            list.add(new DataTypeSupplierContact(result.getInt(colSupplierId), result.getString(colContactName),
                    result.getString(colContactNum)));
        }
        return list;
    }

}