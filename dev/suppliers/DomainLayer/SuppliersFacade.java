package suppliers.DomainLayer;

import java.util.HashMap;
import java.util.List;

public class SuppliersFacade {
    private HashMap<Integer, Supplier> suppliers;
    private static SuppliersFacade instance;

    private SuppliersFacade() {
        suppliers = new HashMap<Integer, Supplier>();
    }

    public static SuppliersFacade getInstance() {
        if (instance == null) {
            instance = new SuppliersFacade();
        }
        return instance;
    }

    public void addSupplier(Supplier supplier) {
        if (suppliers.containsKey(supplier.getId())) {
            throw new IllegalArgumentException("Supplier with ID " + supplier.getId() + " already exists");
        }
        suppliers.put(supplier.getId(), supplier);
    }

    public void addSupplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering) {
        if (suppliers.containsKey(id)) {
            throw new IllegalArgumentException("Supplier with ID " + id + " already exists");
        }
        Supplier supplier = new Supplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories,
                isDelivering);
        suppliers.put(id, supplier);
    }

    public Supplier getSupplier(int supplierId) {
        if (!suppliers.containsKey(supplierId)) {
            throw new IllegalArgumentException("Supplier with ID " + supplierId + " not found");
        }
        return suppliers.get(supplierId);
    }

    public void removeSupplier(int supplierId) {
        suppliers.remove(supplierId);
    }

    public void setSupplierName(int supplierId, String name) {
        getSupplier(supplierId).setName(name);
    }

    public void setSupplierBankAccount(int supplierId, String bankAccount) {
        getSupplier(supplierId).setBankAccount(bankAccount);
    }

    public void setSupplierPaymentOption(int supplierId, String paymentOption) {
        getSupplier(supplierId).setPaymentOption(paymentOption);
    }

    public void setSupplierContacts(int supplierId, HashMap<String, String> contacts) {
        if (contacts.isEmpty())
            throw new IllegalArgumentException("Contacts list cannot be empty");
        getSupplier(supplierId).setContacts(contacts);
    }

    public void setSupplierDeliveryDays(int supplierId, List<Integer> deliveryDays) {
        getSupplier(supplierId).setDeliveryDays(deliveryDays);
    }

    public void setSupplierCategories(int supplierId, HashMap<Category, HashMap<Integer, Product>> categories) {
        if (categories.isEmpty())
            throw new IllegalArgumentException("Categories list cannot be empty");
        getSupplier(supplierId).setCategories(categories);
    }

    public void setSupplierIsDelivering(int supplierId, boolean isDelivering) {
        getSupplier(supplierId).setDelivering(isDelivering);
    }

    public void addSupplierContact(int supplierId, String contactName, String contactDetails) {
        getSupplier(supplierId).addContact(contactName, contactDetails);
    }

    public void removeSupplierContact(int supplierId, String contactName) {
        getSupplier(supplierId).removeContact(contactName);
    }

    public void addSupplierCategory(int supplierId, Category category) {
        getSupplier(supplierId).addCategory(category);
    }

    public void addSupplierDeliveryDay(int supplierId, int day) {
        getSupplier(supplierId).addDeliveryDay(day);
    }

    public void removeSupplierDeliveryDay(int supplierId, int day) {
        getSupplier(supplierId).removeDeliveryDay(day);
    }

    public void addProductToSupplier(int supplierId, Product product) {
        getSupplier(supplierId).addProduct(product.getCategory(), product);
    }

    public Product getProductInSupplier(int supplierId, Category category, int catalogNumber) {
        Product product = getSupplier(supplierId).getProduct(category, catalogNumber);
        if (product == null)
            throw new IllegalArgumentException("Product " + catalogNumber + " not found in supplier " + supplierId);
        return product;
    }

    public Product getProductInSupplier(int supplierId, int catalogNumber) {
        Product product = getSupplier(supplierId).getProduct(catalogNumber);
        if (product == null)
            throw new IllegalArgumentException("Product " + catalogNumber + " not found in supplier " + supplierId);
        return product;
    }

    public void removeProductFromSupplier(int supplierId, Product product) {
        getSupplier(supplierId).removeProduct(product.getCategory(), product);
    }

    public List<Product> getPurchasedProductsFromSupplier(int supplierId) {
        return getSupplier(supplierId).getPurchasedProducts();
    }

    public HashMap<Integer, Product> getAllSupplierProducts(int supplierId) {
        return getSupplier(supplierId).getAllProducts();
    }

}
