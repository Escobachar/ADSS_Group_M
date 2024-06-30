package suppliers.DomainLayer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import suppliers.DataAccessLayer.DAO.OrderDeliveryDayDAO;
import suppliers.DataAccessLayer.DAO.OrderItemDAO;
import suppliers.DaysOfTheWeek.Day;

public class Order {
    final private int orderId;
    private Supplier supplier;
    final private Date creationDate;
    private Date deliveryDate;
    private HashMap<Product, Integer> items;
    private List<Day> constDeliveryDays;
    private double price;
    private boolean isChanged;

    private OrderDeliveryDayDAO deliveryDayDAO;
    private OrderItemDAO itemDAO;

    public Order(int orderId, Supplier supplier, Date creationDate, Date deliveryDate, HashMap<Product, Integer> items,
            List<Day> deliveryDays) {
        this.orderId = orderId;
        this.supplier = supplier;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.items = items;
        priceCalculation();
        this.isChanged = false;
        this.constDeliveryDays = deliveryDays;
        increaseProductOrdersCount();
    }

    private void increaseProductOrdersCount() {
        for (HashMap.Entry<Product, Integer> entry : this.items.entrySet()) {
            Product product = entry.getKey();
            product.incrementOrdersCount();
        }
    }

    private void decreaseProductOrdersCount() {
        for (HashMap.Entry<Product, Integer> entry : this.items.entrySet()) {
            Product product = entry.getKey();
            product.decrementOrdersCount();
        }
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

    public void setConstDeliveryDays(List<Day> deliveryDays) throws SQLException {
        deliveryDayDAO.deleteOrderDeliveryDays(orderId);
        deliveryDayDAO.addOrderDeliveryDays(orderId,deliveryDays);
        this.constDeliveryDays = deliveryDays;
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

    public void setItems(HashMap<Product, Integer> items) throws SQLException {
        decreaseProductOrdersCount();
        this.items = items;
        increaseProductOrdersCount();
        isChanged = true;
        itemDAO.deleteOrderItems(orderId);
        itemDAO.addOrderItems(orderId,getSupplierId(),items);
    }

    public void removeItem(Product product) throws SQLException {
        if (this.items.containsKey(product)) {
            product.decrementOrdersCount();
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
            product.incrementOrdersCount();
            this.items.put(product, amount);
            isChanged = true;
            itemDAO.addOrderItem(orderId,getSupplierId(),product,amount);
        }

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

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
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
}