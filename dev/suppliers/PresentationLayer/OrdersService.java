package suppliers.PresentationLayer;

import suppliers.DomainLayer.Order;
import suppliers.DomainLayer.OrdersFacade;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.SuppliersFacade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrdersService {
    private OrdersFacade of;
    private SuppliersFacade sf;

    public OrdersService() {
        of = OrdersFacade.getInstance();
        sf = SuppliersFacade.getInstance();

    }

    public String addOrder(int supplierID, String deliveryDate, HashMap<Integer, Integer> items,
                         List<Integer> deliveryDays) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(deliveryDate);

            HashMap<Product, Integer> products = new HashMap<>();
            for (HashMap.Entry<Integer, Integer> entry : items.entrySet()) {
                int catalogNumber = entry.getKey();
                products.put(sf.getProductInSupplier(supplierID,catalogNumber), entry.getValue());
            }
            //const day
            int orderId = of.addOrder(SuppliersFacade.getInstance().getSupplier(supplierID), new Date(), date, products, null);
            return "Order "+orderId+" added successfully";

        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public void addOrderConstDeliveryDay(int orderId, int day) {
    //    of.addOrderConstDeliveryDay(orderId, day);

    }

    public void removeOrderConstDeliveryDay(int orderId, int day) {
    //    of.removeOrderConstDeliveryDay(orderId, day);
    }

    public String removeOrder(int orderId) {
        try {
            of.removeOrder(orderId);
            return "Order removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void ChangeOrderItemQuantity(int orderId, Product product, int quantity) {
        of.ChangeOrderItemQuantity(orderId, product, quantity);
    }

    public void setOrderDeliveryDate(int orderId, Date deliveryDate) {
        of.setOrderDeliveryDate(orderId, deliveryDate);
    }

    public String displayOrder(int orderId) {
        String display= "";
        return display;
    }

    public String displayThisWeekOrders() {
        String display= "";
        HashMap<Integer, Order> thisWeekOrders = of.getThisWeekOrders();

        return display;
    }

    public String displayFixedOrderDays(int orderId) {
        String display = "";
        return display;
    }

    public String editProductInOrder(int orderId, int catalogNumber, int amount) {
        String res = "";
        try{
          //  of.ChangeOrderItemQuantity(orderId,);
        } catch (Exception e) {
            return e.getMessage();
        }
        return res;
    }

    public String editDeliveryDate(int orderId, String deliveryDate) {
        String res = "";
        return res;
    }    
}
