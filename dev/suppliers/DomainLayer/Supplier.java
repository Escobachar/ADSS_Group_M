package suppliers.DomainLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Supplier {
    private String name;
    private int id;
    private String bankAccount;
    private String paymentMethod;
    private HashMap<String, String> contacts;
    private String address;
    private List<Integer> deliveryDays;
    private HashMap<Category, HashMap<Integer, Product>> categories;
    private boolean isDelivering;

    public Supplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering, String address) {
        this.name = name;
        this.id = id;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentOption;
        this.contacts = contacts;
        this.deliveryDays = deliveryDays;
        this.categories = categories;
        this.isDelivering = isDelivering;
        this.address = address;
    }

    public Supplier(String name, int id, String bankAccount, String paymentOption, boolean isDelivering,String address) {
        this.name = name;
        this.id = id;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentOption;
        this.contacts = new HashMap<String, String>();
        this.deliveryDays = new LinkedList<Integer>();
        this.categories = new HashMap<Category, HashMap<Integer, Product>>();
        this.isDelivering = isDelivering;
        this.address =address;
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
            return entry.getKey()+ ", "+entry.getValue();
        }
        return "";
    }
    public void setPaymentMethod(String paymentOption) {
        this.paymentMethod = paymentOption;
    }

    public HashMap<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<String, String> contacts) {
        this.contacts = contacts;
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

    public void setDeliveryDays(List<Integer> deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public HashMap<Category, HashMap<Integer, Product>> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<Category, HashMap<Integer, Product>> categories) {
        this.categories = categories;
    }

    public boolean isDelivering() {
        return isDelivering;
    }

    public void setDelivering(boolean delivering) {
        isDelivering = delivering;
    }

    public void addContact(String contactName, String contactDetails) {
        contacts.put(contactName, contactDetails);
    }

    public void removeContact(String contactName) {
        if (contacts.containsKey(contactName))
            contacts.remove(contactName);
    }

    public void addCategory(Category category) {
        if (!categories.containsKey(category))
            categories.put(category, new HashMap<Integer, Product>());
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

    public void removeCategory(Category category) {
        if (categories.containsKey(category))
            categories.remove(category);
    }

    public void addProduct(Category category, Product product) {
        if (!categories.containsKey(category)) {
            addCategory(category);
        }
        categories.get(category).put(product.getCatalogNumber(), product);
    }

    public void removeProduct(int catalogNumber) {
        for (HashMap<Integer, Product> products : categories.values()) {
            if (products.containsKey(catalogNumber))
                products.remove(catalogNumber);
        }
    }

    public void addDeliveryDay(int day) {
        if (!deliveryDays.contains(day))
            deliveryDays.add(day);
    }

    public void removeDeliveryDay(int day) {
        if (deliveryDays.contains(day))
            deliveryDays.remove(day);
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
}