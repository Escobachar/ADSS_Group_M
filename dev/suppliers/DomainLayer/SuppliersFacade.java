package suppliers.DomainLayer;

import java.util.HashMap;
import java.util.List;

public class SuppliersFacade {
    private HashMap<Integer, Supplier> suppliers;

    public SuppliersFacade() {
        suppliers = new HashMap<Integer, Supplier>();
    }

    public void addSupplier(Supplier supplier) {
        suppliers.put(supplier.getId(), supplier);
    }

    public void addSupplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering) {
        Supplier supplier = new Supplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories,
                isDelivering);
        suppliers.put(id, supplier);
    }

    public void removeSupplier(int supplierId) {
        suppliers.remove(supplierId);
    }

    public Supplier getSupplier(int supplierId) {
        return suppliers.get(supplierId);
    }

    public void editSupplierName(int supplierId, String name) {
        suppliers.get(supplierId).setName(name);
    }

    public void editSupplierBankAccount(int supplierId, String bankAccount) {
        suppliers.get(supplierId).setBankAccount(bankAccount);
    }

    public void editSupplierPaymentOption(int supplierId, String paymentOption) {
        suppliers.get(supplierId).setPaymentOption(paymentOption);
    }

    public void editSupplierContacts(int supplierId, HashMap<String, String> contacts) {
        suppliers.get(supplierId).setContacts(contacts);
    }

    public void editSupplierDeliveryDays(int supplierId, List<Integer> deliveryDays) {
        suppliers.get(supplierId).setDeliveryDays(deliveryDays);
    }

    public void editSupplierCategories(int supplierId, HashMap<Category, HashMap<Integer, Product>> categories) {
        suppliers.get(supplierId).setCategories(categories);
    }

    public void editSupplierIsDelivering(int supplierId, boolean isDelivering) {
        suppliers.get(supplierId).setDelivering(isDelivering);
    }

    public void addSupplierContact(int supplierId, String contactName, String contactDetails) {
        suppliers.get(supplierId).addContact(contactName, contactDetails);
    }

    public void removeSupplierContact(int supplierId, String contactName) {
        suppliers.get(supplierId).removeContact(contactName);
    }

    public void addSupplierCategory(int supplierId, Category category) {
        suppliers.get(supplierId).addCategory(category);
    }

    public void addSupplierDeliveryDay(int supplierId, int day) {
        suppliers.get(supplierId).addDeliveryDay(day);
    }

    public void removeSupplierDeliveryDay(int supplierId, int day) {
        suppliers.get(supplierId).removeDeliveryDay(day);
    }

    public void addProductToSupplier(int supplierId, Product product) {
        suppliers.get(supplierId).addProduct(product.getCategory(), product);
    }

    public Product getProductInSupplier(int supplierId, Category category, int catalogNumber) {
        return suppliers.get(supplierId).getProduct(category, catalogNumber);
    }

    public Product getProductInSupplier(int supplierId, int catalogNumber) {
        return suppliers.get(supplierId).getProduct(catalogNumber);
    }

    public void removeProductFromSupplier(int supplierId, Product product) {
        suppliers.get(supplierId).removeProduct(product.getCategory(), product);
    }

}
