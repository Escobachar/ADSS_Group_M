package Suppliers.DomainLayer;

import static Suppliers.DaysOfTheWeek.DayToInt;
import static Suppliers.DaysOfTheWeek.intToDay;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Suppliers.DataAccessLayer.DAO.ProductsDAO;
import Suppliers.DataAccessLayer.DAO.SupplierCategoriesDAO;
import Suppliers.DataAccessLayer.DAO.SupplierContactDAO;
import Suppliers.DataAccessLayer.DAO.SupplierDeliveryDaysDAO;

public class Supplier {
    private String name;
    private int id;
    private String bankAccount;
    private String paymentMethod;
    private HashMap<String, String> contacts;
    private List<Integer> deliveryDays;
    private HashMap<Category, HashMap<Integer, Product>> categories;
    private boolean isDelivering;
    private String address;
    private SupplierCategoriesDAO supplierCategoriesDAO;
    private SupplierContactDAO supplierContactDAO;
    private SupplierDeliveryDaysDAO supplierDeliveryDaysDAO;
    private ProductsDAO productsDAO;


    public Supplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering, String address) throws SQLException {
        this.name = name;
        this.id = id;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentOption;
        this.contacts = contacts;
        this.deliveryDays = deliveryDays;
        this.categories = categories;
        this.isDelivering = isDelivering;
        this.address = address;
        supplierCategoriesDAO = new SupplierCategoriesDAO();
        supplierContactDAO = new SupplierContactDAO();
        supplierDeliveryDaysDAO = new SupplierDeliveryDaysDAO();
        productsDAO = new ProductsDAO();
    }

    public Supplier(String name, int id, String bankAccount, String paymentOption, boolean isDelivering,
            String address) throws SQLException {
        this.name = name;
        this.id = id;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentOption;
        this.contacts = new HashMap<String, String>();
        this.deliveryDays = new LinkedList<Integer>();
        this.categories = new HashMap<Category, HashMap<Integer, Product>>();
        this.isDelivering = isDelivering;
        this.address = address;
        supplierCategoriesDAO = new SupplierCategoriesDAO();
        supplierContactDAO = new SupplierContactDAO();
        supplierDeliveryDaysDAO = new SupplierDeliveryDaysDAO();
        productsDAO = new ProductsDAO();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getOneContact() {
        for (HashMap.Entry<String, String> entry : this.contacts.entrySet()) {
            return entry.getKey() + ", " + entry.getValue();
        }
        return "";
    }

    public void setPaymentMethod(String paymentOption) {
        this.paymentMethod = paymentOption;
    }

    public HashMap<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<String, String> contacts) throws SQLException {
        this.contacts = contacts;
        supplierContactDAO.updateAll(id,contacts);
    }

    public List<Integer> getDeliveryDays() {
        return deliveryDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDeliveryDays(List<Integer> deliveryDays) throws SQLException {
        this.deliveryDays = deliveryDays;
        supplierDeliveryDaysDAO.updateAll(id,deliveryDays);
    }

    public HashMap<Category, HashMap<Integer, Product>> getCategories() {
        return categories;
    }

    public boolean isDelivering() {
        return isDelivering;
    }

    public void setDelivering(boolean delivering) {
        isDelivering = delivering;
    }

    public double getPriceForProduct(String productName, int amount)
    {
        Product product = getProduct(productName);
        if(product == null)
            return Integer.MAX_VALUE;
        return product.calculatePrice(amount);
    }
    public void addContact(String contactName, String contactDetails) throws SQLException {
        contacts.put(contactName, contactDetails);
        supplierContactDAO.insert(id,contactName,contactDetails);
    }

    public void removeContact(String contactName) throws SQLException {
        if (contacts.containsKey(contactName)){
            contacts.remove(contactName);
            supplierContactDAO.delete(id,contactName);
        }

    }

    private boolean isCategoryExist(Category category) {
        for (Category cat : categories.keySet()) {
            if (cat.getCategoryId() == category.getCategoryId())
                return true;
        }
        return false;
    }

    public void addCategory(Category category) throws SQLException {
        if (!isCategoryExist(category)){
            categories.put(category, new HashMap<Integer, Product>());
            supplierCategoriesDAO.addSupplierCategory(id,category.getCategoryId());
        }
    }

    public Product getProduct(int catalogNumber) {
        for (HashMap<Integer, Product> products : categories.values()) {
            for (Product product : products.values()) {
                if (product.getCatalogNumber() == catalogNumber)
                    return product;
            }
        }
        return null;
    }

    public Product getProduct(Category category, int catalogNumber) {
        if (categories.containsKey(category)) {
            return categories.get(category).get(catalogNumber);
        }
        return null;
    }

    public void removeCategory(Category category) throws SQLException {
        if (categories.containsKey(category)){
            categories.remove(category);
            supplierCategoriesDAO.deleteSupplierCategory(id,category.getCategoryId());

        }
    }

    public void addProduct(Category category, Product product) throws SQLException {
        if (!categories.containsKey(category)) {
            addCategory(category);
        }
        categories.get(category).put(product.getCatalogNumber(), product);
        productsDAO.addProduct(id,product);
    }

    public void removeProduct(int catalogNumber) throws SQLException {
        for (HashMap<Integer, Product> products : categories.values()) {
            if (products.containsKey(catalogNumber)){
                Category category = products.get(catalogNumber).getCategory();
                products.remove(catalogNumber);
                if(categories.get(category).isEmpty())
                {
                    removeCategory(category);
                }
                productsDAO.deleteProduct(id,catalogNumber);
            }
        }
    }

    public void addDeliveryDay(int day) throws SQLException {
        if (!deliveryDays.contains(day)){
            deliveryDays.add(day);
            supplierDeliveryDaysDAO.insert(id,intToDay(day));
        }
    }

    public void removeDeliveryDay(int day) throws SQLException {
        if (deliveryDays.contains(day)){
            deliveryDays.remove(day);
            supplierDeliveryDaysDAO.delete(id,day);
        }
    }

    public List<Product> getPurchasedProducts() {
        List<Product> products = new LinkedList<Product>();
        for (HashMap<Integer, Product> productsMap : categories.values()) {
            for (Product product : productsMap.values()) {
                if (product.getOrdersCount() > 0)
                    products.add(product);
            }
        }
        return products;
    }

    public HashMap<Integer, Product> getAllProducts() {
        HashMap<Integer, Product> products = new HashMap<Integer, Product>();
        for (HashMap<Integer, Product> productsMap : categories.values()) {
            for (Product product : productsMap.values()) {
                products.put(product.getCatalogNumber(), product);
            }
        }
        return products;
    }

    public String getContactDetails(String contactName) {
        return contacts.get(contactName);
    }

    @Override
    public String toString() {
        String str = "Supplier{" + "name='" + name + '\'' + ", id=" + id + ", bankAccount='" + bankAccount + '\''
                + ", paymentMethod='" + paymentMethod + '\'';
        if (contacts.size() > 0) {
            str += ", contacts=";
            for (HashMap.Entry<String, String> entry : contacts.entrySet()) {
                str += entry.getKey() + ", " + entry.getValue() + "; ";
            }
        }
        str += ", address='" + address + '\'' + ", isDelivering='" + isDelivering + "\'}";
        return str;
    }

    public boolean isProductExist(Category category, Integer catalogNumber) {
        if(category==null)
        {
            return isProductExist(catalogNumber);
        }
        if(categories.get(category)==null)
            return false;
        return categories.get(category).containsKey(catalogNumber);
    }

    public boolean isProductExist(Integer catalogNumber){
        for (HashMap<Integer, Product> productsMap : categories.values()) {
            for (Integer catalog : productsMap.keySet()) {
                if(catalog.equals(catalogNumber))
                    return true;
            }
        }
        return false;
    }
    public Product getProduct(String productName){
        for (HashMap<Integer, Product> productsMap : categories.values()) {
            for (Product product : productsMap.values()) {
                if(product.getName().equals(productName))
                    return product;
            }
        }
        return null;
    }
}