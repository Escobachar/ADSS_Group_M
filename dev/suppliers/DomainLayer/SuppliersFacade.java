package Suppliers.DomainLayer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import Suppliers.DataAccessLayer.DAO.CategoriesDAO;
import Suppliers.DataAccessLayer.DAO.ProductsDAO;
import Suppliers.DataAccessLayer.DAO.SuppliersDAO;
import Suppliers.DomainLayer.Category;
import Suppliers.DomainLayer.Product;

import java.text.ParseException;

public class SuppliersFacade {
    private HashMap<Integer, Supplier> suppliers;
    private static SuppliersFacade instance = null;
    private HashMap<String, Integer> categories;
    private SuppliersDAO suppliersDAO;
    private CategoriesDAO categoriesDAO;
    private ProductsDAO productsDAO;

    private SuppliersFacade() throws SQLException {
        suppliers = new HashMap<Integer, Supplier>();
        categories = new HashMap<String, Integer>();
        categoriesDAO = new CategoriesDAO();
        suppliersDAO = new SuppliersDAO();
        productsDAO = new ProductsDAO();
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
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering, String address) throws SQLException {
        if (suppliers.containsKey(id)) {
            throw new IllegalArgumentException("Supplier with ID " + id + " already exists");
        }
        Supplier supplier = new Supplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories,
                isDelivering, address);
        suppliers.put(id, supplier);
        suppliersDAO.addSupplier(supplier);
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

    public void removeSupplier(int supplierId) throws SQLException {
        Supplier s = suppliers.remove(supplierId);
        if (s == null)
            throw new IllegalArgumentException("Supplier with ID " + supplierId + " not found");
        else
            suppliersDAO.removeSupplier(supplierId);
    }

    public void setSupplierName(int supplierId, String name) throws SQLException {
        Supplier sup = getSupplier(supplierId);
        sup.setName(name);
        suppliersDAO.updateSupplier(supplierId,sup);
    }

    public void setSupplierBankAccount(int supplierId, String bankAccount) throws SQLException {
        Supplier sup = getSupplier(supplierId);
        sup.setBankAccount(bankAccount);
        suppliersDAO.updateSupplier(supplierId,sup);
    }

    public void setSupplierPaymentOption(int supplierId, String paymentOption) throws SQLException {
        Supplier sup = getSupplier(supplierId);
        sup.setPaymentMethod(paymentOption);
        suppliersDAO.updateSupplier(supplierId,sup);
    }

    public void addSupplierContact(int supplierId, String contactName, String contactDetails) throws SQLException {
        getSupplier(supplierId).addContact(contactName, contactDetails);
    }

    public void removeSupplierContact(int supplierId, String contactName) throws SQLException {
        getSupplier(supplierId).removeContact(contactName);
    }

    public void addProductToSupplier(int supplierId, Product product) throws SQLException {
        getSupplier(supplierId).addProduct(product.getCategory(), product);
        Category category = product.getCategory();
        if(!categories.containsKey(category.getCategoryId())){
            categories.put(category.getCategoryName(),category.getCategoryId());
            categoriesDAO.addCategory(category);
        }
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

    public void removeProductFromSupplier(int supplierId, int catalogNumber) throws SQLException {
        getSupplier(supplierId).removeProduct(catalogNumber);
    }

    public List<Product> getPurchasedProductsFromSupplier(int supplierId) {
        return getSupplier(supplierId).getPurchasedProducts();
    }

    public HashMap<Integer, Product> getAllSupplierProducts(int supplierId) {
        return getSupplier(supplierId).getAllProducts();
    }

    public int getCategoryNumber(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category " + name + " not found");
        return categories.get(name);
    }

    public void setDiscountAmount(int newDiscountAmount, int supplierId, int catalogNumber) throws SQLException {
        getProductInSupplier(supplierId, catalogNumber).setDiscountAmount(supplierId, newDiscountAmount);
    }

    public void setDiscountPercentage(double newDiscount, int supplierId, int catalogNumber) throws SQLException {
        getProductInSupplier(supplierId, catalogNumber).setDiscountPercentage(supplierId, newDiscount);
    }

    public void setProductName(String newName, int supplierId, int catalogNumber) throws SQLException {
        Product product= getProductInSupplier(supplierId, catalogNumber);
        product.setName(newName);
        productsDAO.updateProduct(supplierId,product);
    }

    public void setPrice(double newPrice, int supplierId, int catalogNumber) throws SQLException {
        Product product= getProductInSupplier(supplierId, catalogNumber);
        product.setPrice(newPrice);
        productsDAO.updateProduct(supplierId,product);
    }


    public void changeSupplierIsDelivering(int supplierId, boolean isDelivering) {
        getSupplier(supplierId).setDelivering(isDelivering);
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

    public Supplier getCheapestSupplier(String productName, int amount) {
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

    public void setSupplierAddress(int id, String address) throws SQLException {
        Supplier sup = getSupplier(id);
        sup.setAddress(address);
        suppliersDAO.updateSupplier(id,sup);
    }

    public void retrieveData() throws SQLException, ParseException {
        suppliers = suppliersDAO.getAllSuppliers();
        categories = new CategoriesDAO().getAllCategories();
    }
}