package Suppliers.DomainLayer;

import Suppliers.DataAccessLayer.DAO.OrderDeliveryDayDAO;
import Suppliers.DataAccessLayer.DAO.OrderItemDAO;
import Suppliers.DataAccessLayer.DAO.ProductsDAO;
import Suppliers.DaysOfTheWeek.Day;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Order {
    final private int orderId;
    private Supplier supplier;
    final private Date creationDate;
    private Date deliveryDate;
    private HashMap<Product, Integer> items;
    private List<Day> constDeliveryDays;
    private double price;
    private boolean isChanged;
    private ProductsDAO productsDAO;
    private String branch;

    private OrderDeliveryDayDAO deliveryDayDAO;
    private OrderItemDAO itemDAO;

    public Order(int orderId, Supplier supplier, Date creationDate, Date deliveryDate, HashMap<Product, Integer> items,
            List<Day> deliveryDays, String branch) throws SQLException {
        this.orderId = orderId;
        this.supplier = supplier;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.items = items;
        this.branch = branch;
        priceCalculation();
        this.isChanged = false;
        this.constDeliveryDays = deliveryDays;
        itemDAO= new OrderItemDAO();
        productsDAO = new ProductsDAO();
        deliveryDayDAO = new OrderDeliveryDayDAO();
        increaseProductOrdersCount();
    }

    private void increaseProductOrdersCount() {
        for (HashMap.Entry<Product, Integer> entry : this.items.entrySet()) {
            Product product = entry.getKey();
            product.incrementOrdersCount();

        }
    }

    private void decrementOrdersCountForProduct(Product product) throws SQLException {
        product.decrementOrdersCount();
        productsDAO.updateProduct(supplier.getId(), product);

    }

    public List<Day> getConstDeliveryDays() {

        return constDeliveryDays;
    }

    public String getStringConstDeliveryDays() {
        if (constDeliveryDays.isEmpty())
            return "no fixed delivery days.";
        String toString = "";
        for (Day day : constDeliveryDays) {
            toString += day.name() + ", ";
        }
        return toString.substring(0, toString.length() - 2);
    }

    public void removeConstDeliveryDay(Day day) throws SQLException {
        deliveryDayDAO.deleteOrderDeliveryDay(orderId, day);
        this.constDeliveryDays.remove(day);
    }

    public void addConstDeliveryDay(Day day) throws SQLException {
        if (!this.constDeliveryDays.contains(day)) {
            deliveryDayDAO.addOrderDeliveryDay(orderId, day);
            this.constDeliveryDays.add(day);
        }
    }

    public String orderToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String order = "";
        order += "Order INFO:\n";
        order += "Branch: " + branch + "\n";
        order += "supplier name: " + supplier.getName() + " | address: " + supplier.getAddress() + " | order ID: "
                + orderId + "\n";
        order += "supplier ID: " + supplier.getId() + " | Delivery Date: " + dateFormat.format(deliveryDate)
                + " | tel: "
                + supplier.getOneContact() + "\n";
        order += "_________________________________________________________\n";
        order += "catalog number | product name | amount | price | discount(%) | final price\n";
        for (HashMap.Entry<Product, Integer> entry : this.items.entrySet()) {
            order += entry.getKey().productToString(entry.getValue()) + "\n";
        }
        return order;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getSupplierId() {
        return supplier.getId();
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public HashMap<Product, Integer> getItems() {
        return items;
    }

    public void removeItem(Product product) throws SQLException {
        if (this.items.containsKey(product)) {
            decrementOrdersCountForProduct(product);
            this.items.remove(product);
            isChanged = true;
            itemDAO.deleteOrderItems(orderId,product);

        }
    }

    public void addItem(Product product, Integer amount) throws SQLException {
        if (this.items.containsKey(product)) {
            if (this.items.get(product) == amount)
                return;
            this.items.put(product, amount);
            isChanged = true;
            itemDAO.updateProductAmount(orderId, product.getCatalogNumber(),amount);
        } else {
            incrementOrdersCountForProduct(product);
            this.items.put(product, amount);
            isChanged = true;
            itemDAO.addOrderItem(orderId,getSupplierId(),product,amount);
        }

    }

    private void incrementOrdersCountForProduct(Product product) throws SQLException {
        product.incrementOrdersCount();
        productsDAO.updateProduct(supplier.getId(),product);
    }

    public double getPrice() {
        if (isChanged)
            priceCalculation();
        return price;
    }

    private void priceCalculation() {
        double newPrice = 0;

        for (HashMap.Entry<Product, Integer> entry : this.items.entrySet()) {
            Product product = entry.getKey();
            newPrice += product.calculatePrice(entry.getValue());
        }
        price = newPrice;
        isChanged = false;
    }

    public boolean isDelivering() {
        return supplier.isDelivering();
    }

    public boolean containsItem(Product product) {
        return items.containsKey(product);
    }

    public boolean canEdit() {
        long diffInMillies = getDeliveryDate().getTime() - new Date().getTime();
        if (TimeUnit.MILLISECONDS.toDays(diffInMillies) < 1) {
return false;        }
        return true;
    }

    public String getBranch() {
        return branch;
    }
}