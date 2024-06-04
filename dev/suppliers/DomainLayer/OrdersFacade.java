package suppliers.DomainLayer;

import suppliers.DaysOfTheWeek.Day;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrdersFacade {
    private static OrdersFacade instance;
    private int orderIdCounter;
    private HashMap<Integer, Order> orders;

    private OrdersFacade() {
        orderIdCounter = 1;
        orders = new HashMap<Integer, Order>();
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
        if (deliveryDate.before(creationDate)) {
            throw new IllegalArgumentException("Delivery date must be after creation date");
        }
        if(items == null){
            throw new IllegalArgumentException("Items list is empty");
        }
        if (deliveryDays == null) {
            throw new IllegalArgumentException("Delivery days list is empty");
        }
        Order order = new Order(orderIdCounter, supplier, creationDate, deliveryDate, items,
                deliveryDays);
        orders.put(orderIdCounter, order);
        orderIdCounter++;
        return orderIdCounter--;
    }
    public void addOrder(Order order) {
        if(orders.get(order.getOrderId())!=null){
            throw new IllegalArgumentException("Order already exists");
        }
        orders.put(order.getOrderId(), order);
    }
    public void addOrderConstDeliveryDay(int orderId, Day day) {
        
        getOrder(orderId).addConstDeliveryDay(day);
    }
    public void addOrderConstDeliveryDays(int orderId, List<Day> days) {
        if(days == null || days.isEmpty()){
            throw new IllegalArgumentException("Days list is empty");
        }   
        for (Day day : days) {
            addOrderConstDeliveryDay(orderId, day);
        }
    }
    public void removeOrderConstDeliveryDay(int orderId, Day day) {
        getOrder(orderId).removeConstDeliveryDay(day);
    }
    public void removeOrderConstDeliveryDays(int orderId, List<Day> days) {
        if(days == null || days.isEmpty()){
            throw new IllegalArgumentException("Days list is empty");
        }   
        for (Day day : days) {
            removeOrderConstDeliveryDay(orderId, day);
        }
    }
    public void removeOrder(int orderId) {
        orders.remove(orderId);
    }

    public List<String[]> orderIdToString (int orderId){
        return getOrder(orderId).orderToString();
    }
    public Order getOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        return order;
    }

    // if quantity is 0, remove the item from the order
    public void ChangeOrderItemQuantity(int orderId, Product product, int quantity) {
        Order order = getOrder(orderId);
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
            } else if (order.getDeliveryDate().getTime() - new Date().getTime() < 7 * 24 * 60 * 60 * 1000) {
                thisWeekOrders.put(order.getOrderId(), order);
            }
        }
        return thisWeekOrders;
    }

    public HashMap<Integer, Order> getThisWeekPickupOrders() {
        HashMap<Integer, Order> thisWeekPickupOrders = new HashMap<Integer, Order>();
        for (Order order : orders.values()) {
            if (!order.isDelivering()) {
                if (!order.getConstDeliveryDays().isEmpty()) {
                    thisWeekPickupOrders.put(order.getOrderId(), order);
                } else if (order.getDeliveryDate().getTime() - new Date().getTime() < 7 * 24 * 60 * 60 * 1000) {
                    thisWeekPickupOrders.put(order.getOrderId(), order);
                }
            }
        }
        return thisWeekPickupOrders;
    }

}