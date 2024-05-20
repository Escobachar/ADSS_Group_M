package suppliers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import suppliers.Category;
import suppliers.DiscountQuantity;
import suppliers.Product;

public class Supplier {
    private String name;
    private int id;
    private String bankAccount;
    private String paymentOption;
    private HashMap<String, String> contacts;
    private List<Integer> deliveryDays;
    private HashMap<Category, LinkedList<Product>> categories;
    private boolean isDelivering;

    // Constructor
    public Supplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, LinkedList<Product>> categories, boolean isDelivering) {
        this.name = name;
        this.id = id;
        this.bankAccount = bankAccount;
        this.paymentOption = paymentOption;
        this.contacts = contacts;
        this.deliveryDays = deliveryDays;
        this.categories = categories;
        this.isDelivering = isDelivering;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
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

    public void setDeliveryDays(List<Integer> deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public HashMap<Category, LinkedList<Product>> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<Category, LinkedList<Product>> categories) {
        this.categories = categories;
    }

    public boolean isDelivering() {
        return isDelivering;
    }

    public void setDelivering(boolean delivering) {
        isDelivering = delivering;
    }
}
