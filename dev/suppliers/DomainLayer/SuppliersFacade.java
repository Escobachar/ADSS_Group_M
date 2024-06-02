package suppliers.DomainLayer;

import java.util.HashMap;
import java.util.List;

public class SuppliersFacade {
    private HashMap<Integer, Supplier> suppliers;
    private static SuppliersFacade instance;
    private HashMap<String, Integer> categories;

    private SuppliersFacade() {
        suppliers = new HashMap<Integer, Supplier>();
        categories = new HashMap<String, Integer>();
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
        getSupplier(supplierId).setPaymentMethod(paymentOption);
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

    public void removeProductFromSupplier(int supplierId, int catalogNumber) {
        getSupplier(supplierId).removeProduct(catalogNumber);
    }

    public List<Product> getPurchasedProductsFromSupplier(int supplierId) {
        return getSupplier(supplierId).getPurchasedProducts();
    }

    public HashMap<Integer, Product> getAllSupplierProducts(int supplierId) {
        return getSupplier(supplierId).getAllProducts();
    }

    public void addCategory(String name) {
        int number = categories.size() + 1;
        categories.put(name, number);
    }

    public void removeCategory(String name) {
        categories.remove(name);
    }

    public int getCategoryNumber(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category " + name + " not found");
        return categories.get(name);
    }

    public Category getCategory(String name) {
        return new Category(name, getCategoryNumber(name));
    }

    // price //discount value // discount amount // catalog number
    public void setCatalogNumber(int newCatalogNumber, int supplierId, int catalogNumber) {
        Product product = getProductInSupplier(supplierId, catalogNumber);
        removeProductFromSupplier(supplierId, catalogNumber);
        product.setCatalogNumber(newCatalogNumber);
        addProductToSupplier(supplierId, product);
    }

    public void setDiscountAmount(int newDiscountAmount, int supplierId, int catalogNumber) {
        getProductInSupplier(supplierId, catalogNumber).getDiscount().setAmount(newDiscountAmount);
    }

    public void setDiscountPrecentage(double newDiscount, int supplierId, int catalogNumber) {
        getProductInSupplier(supplierId, catalogNumber).getDiscount().setDiscount(newDiscount);
    }

    public void setProductName(String newName, int supplierId, int catalogNumber) {
        getProductInSupplier(supplierId, catalogNumber).setName(newName);
    }

    public void setPrice(double newPrice, int supplierId, int catalogNumber) {
        getProductInSupplier(supplierId, catalogNumber).setPrice(newPrice);
    }

    public void changeSupplierBankAccount(int supplierId, String bankAccount) {
        getSupplier(supplierId).setBankAccount(bankAccount);
    }

    public void changeSupplierPaymentOption(int supplierId, String paymentOption) {
        getSupplier(supplierId).setPaymentMethod(paymentOption);
    }

    public void changeSupplierIsDelivering(int supplierId, boolean isDelivering) {
        getSupplier(supplierId).setDelivering(isDelivering);
    }

    public void changeSupplierName(int supplierId, String name) {
        getSupplier(supplierId).setName(name);
    }

    public void changeSupplierPaymentMethod(int supplierId, String paymentMethod) {
        getSupplier(supplierId).setPaymentMethod(paymentMethod);
    }
}
