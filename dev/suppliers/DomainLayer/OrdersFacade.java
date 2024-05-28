package suppliers.DomainLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrdersFacade {
    private int orderIdCounter;
    private HashMap<Integer, Order> orders;

    public OrdersFacade() {
        orderIdCounter = 1;
        orders = new HashMap<Integer, Order>();
    }

    public void addOrder(int supplierId, Date creationDate, Date deliveryDate, HashMap<Product, Integer> items,
            boolean isDelivering, List<Day> deliveryDays) {
        Order order = new Order(orderIdCounter, supplierId, creationDate, deliveryDate, items, isDelivering,
                deliveryDays);
        orders.put(orderIdCounter, order);
        orderIdCounter++;
    }

    public void addOrderDeliveryDay(int orderId, Day day) {
        orders.get(orderId).addConstDeliveryDay(day);
    }

    public void removeOrderDeliveryDay(int orderId, Day day) {
        orders.get(orderId).removeConstDeliveryDay(day);
    }

    public void removeOrder(int orderId) {
        orders.remove(orderId);
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    // if quantity is 0, remove the item from the order
    public void editOrderItem(int orderId, Product product, int quantity) {
        if (quantity <= 0) {
            orders.get(orderId).removeItem(product);
        } else
            orders.get(orderId).addItem(product, quantity);
    }

    public void editOrderDeliveryDate(int orderId, Date deliveryDate) {
        orders.get(orderId).setDeliveryDate(deliveryDate);
    }

    public void editOrderIsDelivering(int orderId, boolean isDelivering) {
        orders.get(orderId).setDelivering(isDelivering);
    }

    public double getOrderPrice(int orderId) {
        return orders.get(orderId).getPrice();
    }

    public HashMap<Integer, Order> getThisWeekOrders() {
        HashMap<Integer, Order> thisWeekOrders = new HashMap<Integer, Order>();
        for (Order order : orders.values()) {
            if (order.getConstDeliveryDays() != null) {
                thisWeekOrders.put(order.getOrderId(), order);
            } else if (order.getDeliveryDate().getTime() - new Date().getTime() < 7 * 24 * 60 * 60 * 1000) {
                thisWeekOrders.put(order.getOrderId(), order);
            }
        }
        return thisWeekOrders;
    }
}
