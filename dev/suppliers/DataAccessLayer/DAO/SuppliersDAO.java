package suppliers.DataAccessLayer.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import suppliers.DataAccessLayer.DAO.SupplierContactDAO.DataTypeSupplierContact;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DaysOfTheWeek;
import suppliers.DaysOfTheWeek.Day;
import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Supplier;

import static suppliers.DaysOfTheWeek.intToDay;

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
        this.conn = DataBase.getConnection();
        productsDAO = new ProductsDAO();
        supplierCategoriesDAO = new SupplierCategoriesDAO();
        supplierDeliveryDaysDAO =new SupplierDeliveryDaysDAO();
        supplierContactDAO = new SupplierContactDAO();

    }

    public void addSupplier(Supplier supplier) throws SQLException {
        String query = "INSERT INTO " + tableName + " (" + colSupplierId + ", " + colSupplierName + ", "
                + colSupplierBankAccount + ", " + colSupplierPaymantOption + ", " + colIsDelivering + ", "
                + colSupplierAddress + ") VALUES (" + supplier.getId() + ", '" + supplier.getName() + "', '"
                + supplier.getBankAccount() + "', '" + supplier.getPaymentMethod() + "', " + supplier.isDelivering()
                + ", '" + supplier.getAddress() + "')";
        conn.createStatement().executeUpdate(query);
        int id = supplier.getId();
        List<Day> dayDeliveryDays = new ArrayList<>();
        for (Integer day:supplier.getDeliveryDays()) {
            dayDeliveryDays.add(intToDay(day));
        }
        if(!dayDeliveryDays.isEmpty())
            supplierDeliveryDaysDAO.insertAll(id, dayDeliveryDays);
        List<Integer> categories = new ArrayList<>();
        for (Category category:supplier.getCategories().keySet()) {
            categories.add(category.getCategoryId());
        }
        supplierCategoriesDAO.addAllSupplierCategory(id,categories);
        supplierContactDAO.insertAll(id,supplier.getContacts());
    }

    public void removeSupplier(int supplierId) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + colSupplierId + " = " + supplierId;
        conn.createStatement().executeUpdate(query);
        supplierDeliveryDaysDAO.deleteAll(supplierId);
        supplierContactDAO.deleteAll(supplierId);
        supplierCategoriesDAO.deleteSupplierCategories(supplierId);
        productsDAO.deleteAllProductBySupplier(supplierId);

    }

    public Supplier getSupplierById(int supplierId) throws SQLException {

        List<DataTypeSupplierContact> contacts = supplierContactDAO.select(supplierId);
        HashMap<String, String> contactsMap = new HashMap<>();
        for (DataTypeSupplierContact contact : contacts) {
            contactsMap.put(contact.contactName, contact.contactNum);
        }
        List<Day> deliveryDays = supplierDeliveryDaysDAO.select(supplierId);
        List<Integer> deliveryDaysInt = new ArrayList<>();
        for (Day day : deliveryDays) {
            deliveryDaysInt.add(DaysOfTheWeek.DayToInt(day));
        }

        List<Integer> categoriesInt = supplierCategoriesDAO.getCategoryBySupplierId(supplierId);
        HashMap<Category, HashMap<Integer, Product>> categories = new HashMap<>();
        CategoriesDAO categoriesDAO = new CategoriesDAO();

        for (int categoryId : categoriesInt) {
            categories.put(categoriesDAO.getCategoryById(categoryId), productsDAO.getCategoryProducts(supplierId,categoryId));
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
    public void updateSupplier(int sid, Supplier supplier) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Suppliers SET name = ?, bankAccount = ?, paymentOption = ?, address = ? WHERE SupplierId = ?");
        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getBankAccount());
        stmt.setString(3, supplier.getPaymentMethod());
        stmt.setString(4, supplier.getAddress());
        stmt.executeUpdate();
    }

}