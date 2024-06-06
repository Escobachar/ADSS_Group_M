package suppliers.PresentationLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import suppliers.DomainLayer.*;

public class Initialize {
    public OrdersService os = OrdersService.getInstance();
    public SuppliersService ss = SuppliersService.getInstance();

    Initialize() {
        List<Integer> l2 = new LinkedList<Integer>();
        l2.add(1);
        l2.add(4);
        ss.addSupplier("Supplier1", 1, "bank1", "payment1", new HashMap<String, String>(), new LinkedList<Integer>(),
                new HashMap<Category, HashMap<Integer, Product>>(), false, "address1");
        ss.addSupplier("Supplier2", 2, "bank2", "payment2", new HashMap<String, String>(), l2,
                new HashMap<Category, HashMap<Integer, Product>>(), true, "address2");
        ss.addSupplier("Supplier3", 3, "bank3", "payment3", new HashMap<String, String>(), new LinkedList<Integer>(),
                new HashMap<Category, HashMap<Integer, Product>>(), true, "address3");
        ss.addSupplierContact(1, "contact1", "123456789");
        ss.addSupplierContact(1, "contact2", "987654321");
        ss.addSupplierContact(2, "contact3", "123456789");
        Category c1 = new Category("category1", 1);
        Category c2 = new Category("category2", 2);
        Product p1 = new Product("product1", 1, 1, c1, new DiscountQuantity(1, 3, 10));
        Product p2 = new Product("product2", 2, 2, c2, new DiscountQuantity(2, 5, 20));
        ss.addProduct(1, p1);
        ss.addProduct(2, p2);
        
    }

}
