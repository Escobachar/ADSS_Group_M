package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import suppliers.DataAccessLayer.DAO.SupplierContactDAO.DataTypeSupplierContact;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import suppliers.DaysOfTheWeek.Day;
import static suppliers.DaysOfTheWeek.intToDay;
import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Supplier;

public class SuppliersDAO {
    private final String colSupplierId = "id";
    private final String colSupplierName = "name";
    private final String colSupplierBankAccount = "bankAccount";
    private final String colSupplierPaymantOption = "paymentOption";
    private final String colIsDelivering = "isDelivering";
    private final String colSupplierAddress = "address";
    private final String tableName = "Suppliers";
    private ProductsDAO productsDAO;
    private SupplierCategoriesDAO supplierCategoriesDAO;
    private SupplierDeliveryDaysDAO supplierDeliveryDaysDAO;
    private SupplierContactDAO supplierContactDAO;
    

    private Connection conn = null;

    public SuppliersDAO() throws SQLException {
        productsDAO = new ProductsDAO();
        supplierCategoriesDAO = new SupplierCategoriesDAO();
        supplierDeliveryDaysDAO =new SupplierDeliveryDaysDAO();
        supplierContactDAO = new SupplierContactDAO();

    }

    public void addSupplier(Supplier supplier) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (" + colSupplierId + ", " + colSupplierName + ", "
                + colSupplierBankAccount + ", " + colSupplierPaymantOption + ", " + colIsDelivering + ", "
                + colSupplierAddress + ") VALUES (?,?,?,?,?,?)");
        String query = "INSERT INTO " + tableName + " (" + colSupplierId + ", " + colSupplierName + ", "
                + colSupplierBankAccount + ", " + colSupplierPaymantOption + ", " + colIsDelivering + ", "
                + colSupplierAddress + ") VALUES (" + supplier.getId() + ", '" + supplier.getName() + "', '"
                + supplier.getBankAccount() + "', '" + supplier.getPaymentMethod() + "', " + supplier.isDelivering()
                + ", '" + supplier.getAddress() + "')";
        stmt.setInt(1, supplier.getId());
        stmt.setString(2, supplier.getName());
        stmt.setString(3, supplier.getBankAccount());
        stmt.setString(4, supplier.getPaymentMethod());
        if (supplier.isDelivering()) {
            stmt.setInt(5, 1);
        } else {
            stmt.setInt(5, 0);
        }
        stmt.setString(6, supplier.getAddress());
        stmt.executeUpdate();
        
        int id = supplier.getId();
        List<Day> dayDeliveryDays = new ArrayList<>();
        for (Integer day:supplier.getDeliveryDays()) {
            dayDeliveryDays.add(intToDay(day));
        }
        if(!dayDeliveryDays.isEmpty())
            supplierDeliveryDaysDAO.insertAll(id, dayDeliveryDays);
        List<Integer> categories = new ArrayList<>();
        List<Product> items = new ArrayList<>();
        for (Map.Entry<Category, HashMap<Integer, Product>> categoriesItems:supplier.getCategories().entrySet()) {
            categories.add(categoriesItems.getKey().getCategoryId());
            for (Product product: categoriesItems.getValue().values()) {
                items.add(product);
            }
        }
        supplierCategoriesDAO.addAllSupplierCategory(id,categories);
        supplierContactDAO.insertAll(id,supplier.getContacts());
        productsDAO.insertAllProducts(id, items);
    }

    public void removeSupplier(int supplierId) throws SQLException {
        this.conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE " + colSupplierId +"= ?");
        stmt.setInt(1, supplierId);
        stmt.executeUpdate();
        
        supplierDeliveryDaysDAO.deleteAll(supplierId);
        supplierContactDAO.deleteAll(supplierId);
        supplierCategoriesDAO.deleteSupplierCategories(supplierId);
        productsDAO.deleteAllProductBySupplier(supplierId);

    }

    public Supplier getSupplierById(int supplierId) throws SQLException {
        this.conn = DataBase.getConnection();
        HashMap<String, String> contactsMap = getSupplierContacts(supplierId);
        List<Integer> deliveryDaysInt = getSupplierDeliveryDays(supplierId);
        HashMap<Category, HashMap<Integer, Product>> categories = getSupplierCategories(supplierId);

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Suppliers WHERE id = ?");
        stmt.setInt(1, supplierId);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            Supplier sup = new Supplier(
                    result.getString(colSupplierName),
                    result.getInt(colSupplierId),
                    result.getString(colSupplierBankAccount),
                    result.getString(colSupplierPaymantOption),
                    contactsMap,
                    deliveryDaysInt,
                    categories,
                    result.getBoolean(colIsDelivering),
                    result.getString(colSupplierAddress));
            
            return sup;
        }
        
        throw new SQLException("No such supplier");
    }

    private HashMap<Category, HashMap<Integer, Product>> getSupplierCategories(int supplierId) throws SQLException{
        List<Integer> categoriesInt = supplierCategoriesDAO.getCategoryBySupplierId(supplierId);
        HashMap<Category, HashMap<Integer, Product>> categories = new HashMap<>();
        CategoriesDAO categoriesDAO = new CategoriesDAO();

        for (int categoryId : categoriesInt) {
            categories.put(categoriesDAO.getCategoryById(categoryId), productsDAO.getCategoryProducts(supplierId,categoryId));
        }
        return categories;
    }

    private List<Integer> getSupplierDeliveryDays(int supplierId) throws SQLException{
        List<Day> deliveryDays = supplierDeliveryDaysDAO.select(supplierId);
        List<Integer> deliveryDaysInt = new ArrayList<>();
        for (Day day : deliveryDays) {
            deliveryDaysInt.add(DaysOfTheWeek.DayToInt(day));
        }
        return deliveryDaysInt;
    }

    private HashMap<String,String> getSupplierContacts(int supplierId) throws SQLException{
        List<DataTypeSupplierContact> contacts = supplierContactDAO.select(supplierId);
        HashMap<String, String> contactsMap = new HashMap<>();
        for (DataTypeSupplierContact contact : contacts) {
            contactsMap.put(contact.contactName, contact.contactNum);
        }
        return contactsMap;
    }

    public HashMap<Integer, Supplier> getAllSuppliers() throws SQLException {
        this.conn = DataBase.getConnection();
        HashMap<Integer, Supplier> suppliers = new HashMap<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Suppliers");
        
        while (rs.next()) {
            int supplierId = rs.getInt(colSupplierId);
            HashMap<String, String> contactsMap = getSupplierContacts(supplierId);
            List<Integer> deliveryDaysInt = getSupplierDeliveryDays(supplierId);
            HashMap<Category, HashMap<Integer, Product>> categories = getSupplierCategories(supplierId);    
            Supplier supplier = new Supplier(
                    rs.getString(colSupplierName),
                    supplierId,
                    rs.getString(colSupplierBankAccount),
                    rs.getString(colSupplierPaymantOption),
                    contactsMap,
                    deliveryDaysInt,
                    categories,
                    rs.getBoolean(colIsDelivering),
                    rs.getString(colSupplierAddress));
            suppliers.put(supplierId, supplier);
        }
        
        return suppliers;
    }


    public void updateSupplier(int sid, Supplier supplier) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Suppliers SET name = ?, bankAccount = ?, paymentOption = ?, address = ? WHERE id = ?");
        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getBankAccount());
        stmt.setString(3, supplier.getPaymentMethod());
        stmt.setString(4, supplier.getAddress());
        stmt.executeUpdate();
    }

}