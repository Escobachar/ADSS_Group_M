package suppliers.DomainLayer;

import suppliers.DaysOfTheWeek.Day;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrdersFacade {
    private static OrdersFacade instance;
    private int orderIdCounter;
    private HashMap<Integer, Order> orders;

    private OrdersFacade() {
        orderIdCounter = 1;
        orders = new HashMap<>();
    }

    public static OrdersFacade getInstance() {
        if (instance == null) {
            instance = new OrdersFacade();
        }
        return instance;
    }

    public int addOrder(Supplier supplier, Date creationDate, Date deliveryDate,
            HashMap<Product, Integer> items,
            List<Day> deliveryDays) {
        if (deliveryDate == null || deliveryDate.before(creationDate)) {
            throw new IllegalArgumentException("Delivery date must be after creation date");
        }
        if (items == null) {
            throw new IllegalArgumentException("Items list is empty");
        }
        if (deliveryDays == null) {
            throw new IllegalArgumentException("Delivery days list is empty");
        }
        Order order = new Order(orderIdCounter, supplier, creationDate, deliveryDate, items,
                deliveryDays);
        orders.put(orderIdCounter, order);
        orderIdCounter++;
        return orderIdCounter - 1;
    }

    public void addOrderConstDeliveryDay(int orderId, Day day) {

        getOrder(orderId).addConstDeliveryDay(day);
    }

    public void addOrderConstDeliveryDays(int orderId, List<Day> days) {
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days list is empty");
        }
        for (Day day : days) {
            addOrderConstDeliveryDay(orderId, day);
        }
    }

    public String getToStringConstDeliveringDays(int orderId) {
        return getOrder(orderId).getStringConstDeliveryDays();
    }

    public void removeOrderConstDeliveryDay(int orderId, Day day) {
        getOrder(orderId).removeConstDeliveryDay(day);
    }

    public void removeOrderConstDeliveryDays(int orderId, List<Day> days) {
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days list is empty");
        }
        for (Day day : days) {
            removeOrderConstDeliveryDay(orderId, day);
        }
    }

    public void removeOrder(int orderId) {
        Order o = orders.remove(orderId);
        if (o == null) {
            throw new IllegalArgumentException("Order not found");
        }
    }

    public String orderIdToString(int orderId) {
        return getOrder(orderId).orderToString();
    }

    public Order getOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        return order;
    }
    public boolean isOrderExists(int orderId){
        return orders.containsKey(orderId);
    }

    // if quantity is 0, remove the item from the order
    public void ChangeOrderItemQuantity(int orderId, int catalogNumber, int quantity) {
        Order order = getOrder(orderId);
        int supplierId = order.getSupplierId();

        Product product = SuppliersFacade.getInstance().getProductInSupplier(supplierId, catalogNumber);
        if (!order.containsItem(product)) {
            throw new IllegalArgumentException("Product not found in order " + orderId);
        }
        if (quantity <= 0) {
            order.removeItem(product);
        } else
            order.addItem(product, quantity); // addItem removes the product and adds it again with the new quantity
    }

    public void setOrderDeliveryDate(int orderId, Date deliveryDate) {
        Order order = getOrder(orderId);
        if (deliveryDate.before(new Date())) { // new date = now
            throw new IllegalArgumentException("Delivery date must be after today");
        }
        order.setDeliveryDate(deliveryDate);
    }

    public double getOrderPrice(int orderId) {
        Order order = getOrder(orderId);
        return order.getPrice();
    }

    public HashMap<Integer, Order> getThisWeekOrders() {
        HashMap<Integer, Order> thisWeekOrders = new HashMap<Integer, Order>();
        for (Order order : orders.values()) {
            if (order.getConstDeliveryDays() != null && !order.getConstDeliveryDays().isEmpty()) {
                thisWeekOrders.put(order.getOrderId(), order);
            } else {
                long diffInMillies = order.getDeliveryDate().getTime() - new Date().getTime();
                if (TimeUnit.MILLISECONDS.toDays(diffInMillies) < 7) {
                    thisWeekOrders.put(order.getOrderId(), order);
                }
            }
        }
        return thisWeekOrders;
    }

    public HashMap<Integer, Order> getThisWeekPickupOrders() {
        HashMap<Integer, Order> thisWeekOrders = this.getThisWeekOrders();
        HashMap<Integer, Order> thisWeekPickupOrders = new HashMap<Integer, Order>();
        for (Order order : thisWeekOrders.values()) {
            if (!order.isDelivering()) {
                thisWeekPickupOrders.put(order.getOrderId(), order);
            }
        }
        return thisWeekPickupOrders;
    }

    public String OrdersToString(HashMap<Integer, Order> ordersList) {
        String toString = "";
        for (Order order : ordersList.values()) {
            toString += order.orderToString() + "\n\n";
        }
        return toString;
    }

    public double calcOrderPrice(int orderId) {
        Order order = getOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        return order.getPrice();
    }

    public boolean isOrderCanBeEdit(int orderId) {
        return getOrder(orderId).canEdit();
    }
}