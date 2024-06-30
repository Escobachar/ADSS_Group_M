package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import suppliers.DataAccessLayer.DAO.SupplierContactDAOImpl.DataTypeSupplierContact;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import suppliers.DaysOfTheWeek.Day;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Supplier;

public class SuppliersDAOImpl {
    private final String colSupplierId = "id";
    private final String colSupplierName = "name";
    private final String colSupplierBankAccount = "bankAccount";
    private final String colSupplierPaymantOption = "paymentOption";
    private final String colIsDelivering = "isDelivering";
    private final String colSupplierAddress = "address";
    private final String tableName = "Suppliers";

    private Connection conn = null;

    public SuppliersDAOImpl() {
        this.conn = DataBase.getConn().conn;
    }

    public void addSupplier(Supplier supplier) throws SQLException{
        String query = "INSERT INTO " + tableName + " (" + colSupplierId + ", " + colSupplierName + ", " + colSupplierBankAccount + ", " + colSupplierPaymantOption + ", " + colIsDelivering + ", " + colSupplierAddress + ") VALUES (" + supplier.getId() + ", '" + supplier.getName() + "', '" + supplier.getBankAccount() + "', '" + supplier.getPaymentMethod() + "', " + supplier.isDelivering() + ", '" + supplier.getAddress() + "')";
        conn.createStatement().executeUpdate(query);
    }

    public void removeSupplier(int supplierId) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        conn.createStatement().executeUpdate(query);
    }

    public Supplier getSupplierById(int supplierId) throws SQLException {
        
        SupplierContactDAOImpl supplierContactDAO = new SupplierContactDAOImpl();
        List<DataTypeSupplierContact> contacts= supplierContactDAO.select(supplierId);
        HashMap<String,String> contactsMap = new HashMap<>();
        for (DataTypeSupplierContact contact : contacts) {
            contactsMap.put(contact.contactName, contact.contactNum);
        }
        SupplierDeliveryDaysDAOImpl supplierDeliveryDaysDAO = new SupplierDeliveryDaysDAOImpl();
        List<Day> deliveryDays = supplierDeliveryDaysDAO.select(supplierId);
        List<Integer> deliveryDaysInt = new ArrayList<>();
        for (Day day : deliveryDays) {
            deliveryDaysInt.add(DaysOfTheWeek.DayToInt(day));
        }
        
        SupplierCategoriesDAOImpl supplierCategoriesDAO = new SupplierCategoriesDAOImpl();
        List<Integer> categories = supplierCategoriesDAO.getCategoryBySupplierId(supplierId);
        ProductDAOImpl productDAO = new ProductDAOImpl();
        HashMap<Integer, HashMap<Integer, Product>> categories = new HashMap<>();
        categoriesDAOImpl categoriesDAO = new categoriesDAOImpl();
        for (Integer categoryId : categories) {
            
            categories.put(categoryId, productDAO.getCategoryProducts(supplierId, categoryId));
        }
        
        String query = "SELECT * FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        ResultSet result = conn.createStatement().executeQuery(query);
        if (result.next()) {
            return new Supplier(
                result.getString(colSupplierName),
                result.getInt(colSupplierId), 
                result.getString(colSupplierBankAccount), 
                result.getString(colSupplierPaymantOption),
                contactsMap, 
                deliveryDaysInt,
                categories,
                result.getBoolean(colIsDelivering),  
                result.getString(colSupplierAddress));
        }
        throw new SQLException("No such supplier");
    }
    
}
