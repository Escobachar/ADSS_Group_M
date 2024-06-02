package suppliers.PresentationLayer;

import java.util.HashMap;
import java.util.List;

import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.SuppliersFacade;

public class SuppliersService {

    public String addSupplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering) {
        try {
            SuppliersFacade.getInstance().addSupplier(name, id, bankAccount, paymentOption, contacts, deliveryDays,
                    categories, isDelivering);
            return "Supplier added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
