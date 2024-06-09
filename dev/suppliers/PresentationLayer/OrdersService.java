package suppliers.PresentationLayer;

import suppliers.DaysOfTheWeek;
import suppliers.DomainLayer.OrdersFacade;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.SuppliersFacade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrdersService {
    private OrdersFacade of;
    private SuppliersFacade sf;
    private static OrdersService instance;

    private OrdersService() {
        of = OrdersFacade.getInstance();
        sf = SuppliersFacade.getInstance();

    }

    public static OrdersService getInstance() {
        if (instance == null) {
            instance = new OrdersService();
        }
        return instance;
    }

    public String addOrder(int supplierID, String deliveryDate, HashMap<Integer, Integer> items,
            List<Integer> deliveryDays) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(deliveryDate);

            HashMap<Product, Integer> products = new HashMap<>();
            for (HashMap.Entry<Integer, Integer> entry : items.entrySet()) {
                int catalogNumber = entry.getKey();
                products.put(sf.getProductInSupplier(supplierID, catalogNumber), entry.getValue());
            }
            List<DaysOfTheWeek.Day> constDays = new ArrayList<>();
            for (Integer day : deliveryDays) {
                constDays.add(DaysOfTheWeek.intToDay(day));
            }
            int orderId = of.addOrder(sf.getSupplier(supplierID), new Date(), date, products, constDays);
            return "Order " + orderId + " added successfully";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addOrderConstDeliveryDay(int orderId, int day) {
        try {
            of.addOrderConstDeliveryDay(orderId, DaysOfTheWeek.intToDay(day));
            return "Order fixed day removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeOrderConstDeliveryDay(int orderId, int day) {
        try {
            of.removeOrderConstDeliveryDay(orderId, DaysOfTheWeek.intToDay(day));
            return "Order fixed day removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String removeOrder(int orderId) {
        try {
            of.removeOrder(orderId);
            return "Order removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displayOrder(int orderId) {
        try {
            return of.orderIdToString(orderId);
        } catch (Exception e) {
            return "order does not exist";
        }
    }

    public String displayThisWeekOrders() {
        return of.OrdersToString(of.getThisWeekOrders());
    }

    public String displayFixedOrderDays(int orderId) {
        try {
            return of.getToStringConstDeliveringDays(orderId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String editProductInOrder(int orderId, int catalogNumber, int amount) {
        String res = "";
        try {
            of.ChangeOrderItemQuantity(orderId, catalogNumber, amount);
        } catch (Exception e) {
            return e.getMessage();
        }
        return res;
    }

    public String editDeliveryDate(int orderId, String deliveryDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(deliveryDate);
            of.setOrderDeliveryDate(orderId, date);
            return "Order delivery date updated successfully";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displyOrderPrice(int orderId) {
        try {
            return "Order price: " + of.getOrderPrice(orderId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}