package suppliers.DomainLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

public class Order {
    final private int orderId;
    private Supplier supplier;
    final private Date creationDate;
    private Date deliveryDate;
    private HashMap<Product, Integer> items;
    private List<Day> constDeliveryDays;
    private double price;
    private boolean isChanged;

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

    public void setConstDeliveryDays(List<Day> deliveryDays) {
        this.constDeliveryDays = deliveryDays;
    }

    public void removeConstDeliveryDay(Day day) {
        this.constDeliveryDays.remove(day);
    }

    public void addConstDeliveryDay(Day day) {
        if (!this.constDeliveryDays.contains(day)) {
            this.constDeliveryDays.add(day);
        }
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

    public void setItems(HashMap<Product, Integer> items) {
        decreaseProductOrdersCount();
        this.items = items;
        increaseProductOrdersCount();
        isChanged = true;
    }

    public void removeItem(Product product) {
        if (this.items.containsKey(product)) {
            product.decrementOrdersCount();
            this.items.remove(product);
            isChanged = true;

        }
    }

    public void addItem(Product product, Integer amount) {
        if (this.items.containsKey(product)) {
            if (this.items.get(product) == amount)
                return;
            this.items.put(product, amount);
            isChanged = true;
        } else {
            product.incrementOrdersCount();
            this.items.put(product, amount);
            isChanged = true;
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

}