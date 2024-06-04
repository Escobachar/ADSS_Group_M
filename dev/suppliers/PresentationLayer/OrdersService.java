package suppliers.PresentationLayer;

import suppliers.DomainLayer.Order;
import suppliers.DomainLayer.OrdersFacade;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.SuppliersFacade;
import suppliers.DomainLayer.Supplier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.Date;
import static suppliers.Day;

public class OrdersService {
    private OrdersFacade ordersFacade;

    public OrdersService() {
        ordersFacade = OrdersFacade.getInstance();
    }

    public void addOrder(int supplierID, String deliveryDate, String items,
            String deliveryDays) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(deliveryDate);
            ordersFacade.addOrder(SuppliersFacade.getInstance().getSupplier(supplierID), new Date(), date, null, null);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void addOrderConstDeliveryDay(int orderId, Day day) {
        ordersFacade.addOrderConstDeliveryDay(orderId, day);
    }

    public void removeOrderConstDeliveryDay(int orderId, enums day) {
        ordersFacade.removeOrderConstDeliveryDay(orderId, day);
    }

    public void removeOrder(int orderId) {
        ordersFacade.removeOrder(orderId);
    }

    public Order getOrder(int orderId) {
        return ordersFacade.getOrder(orderId);
    }

    public void ChangeOrderItemQuantity(int orderId, Product product, int quantity) {
        ordersFacade.ChangeOrderItemQuantity(orderId, product, quantity);
    }

    public void setOrderDeliveryDate(int orderId, Date deliveryDate) {
        ordersFacade.setOrderDeliveryDate(orderId, deliveryDate);
    }
    
}
