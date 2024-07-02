package suppliers.DomainLayer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import suppliers.DataAccessLayer.DAO.SuppliersDAO;
import suppliers.DataAccessLayer.DAO.CategoriesDAO;
import java.text.ParseException;

public class SuppliersFacade {
    private HashMap<Integer, Supplier> suppliers;
    private static SuppliersFacade instance;
    private HashMap<String, Integer> categories;
    private SuppliersDAO suppliersDAO;

    private SuppliersFacade() throws SQLException {
        suppliers = new HashMap<Integer, Supplier>();
        categories = new HashMap<String, Integer>();
        suppliersDAO = new SuppliersDAO();
    }

    public static SuppliersFacade getInstance() throws SQLException {
        if (instance == null) {
            try {
                instance = new SuppliersFacade();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // instance = new SuppliersFacade();
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
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering, String address) {
        if (suppliers.containsKey(id)) {
            throw new IllegalArgumentException("Supplier with ID " + id + " already exists");
        }
        Supplier supplier = new Supplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories,
                isDelivering, address);
        suppliers.put(id, supplier);
    }

    public Supplier getSupplier(int supplierId) {
        if (!isSupplierExists(supplierId)) {
            throw new IllegalArgumentException("Supplier with ID " + supplierId + " not found");
        }
        return suppliers.get(supplierId);
    }

    public boolean isSupplierExists(int supplierId) {
        return suppliers.containsKey(supplierId);

    }

    public void removeSupplier(int supplierId) {
        Supplier s = suppliers.remove(supplierId);
        if (s == null)
            throw new IllegalArgumentException("Supplier with ID " + supplierId + " not found");
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

    public void editAddress(int supplierId, String address) {
        getSupplier(supplierId).setAddress(address);
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

    public boolean isProductExistsInSupplier(int id, Category category, Integer catalogNumber) {
        return getSupplier(id).isProductExist(category, catalogNumber);
    }

    public HashMap<Integer, HashMap<Product, Integer>> getCheapestProducts(HashMap<String, Integer> productToOrder) {
        HashMap<Integer, HashMap<Product, Integer>> supplierToOrder = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry : productToOrder.entrySet()) {
            Supplier supplier = getCheapestSupplier(entry.getKey(), entry.getValue());
            if (supplier != null) {
                int supplierId = supplier.getId();
                if (supplierToOrder.containsKey(supplierId))
                    supplierToOrder.get(supplierId).put(supplier.getProduct(entry.getKey()), entry.getValue());
                else {
                    HashMap<Product, Integer> products = new HashMap<>();
                    products.put(supplier.getProduct(entry.getKey()), entry.getValue());
                    supplierToOrder.put(supplierId, products);
                }
            }
        }
        return supplierToOrder;
    }

    private Supplier getCheapestSupplier(String productName, int amount) {
        Supplier sup = null;
        double priceAfterDiscount = Integer.MAX_VALUE;
        for (Supplier supplier : suppliers.values()) {
            double supplierPrice = supplier.getPriceForProduct(productName, amount);
            if (supplierPrice < priceAfterDiscount) {
                sup = supplier;
                priceAfterDiscount = supplierPrice;
            }
        }
        return sup;
    }

    public boolean isProductExists(String productName) {
        for (Supplier supplier : suppliers.values()) {
            Product product = supplier.getProduct(productName);
            if (product != null) {
                return true;
            }
        }
        return false;
    }

    public void retrieveData() throws SQLException, ParseException {
        suppliers = suppliersDAO.getAllSuppliers();
        categories = new CategoriesDAO().getAllCategories();
    }
}