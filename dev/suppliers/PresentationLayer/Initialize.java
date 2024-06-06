package suppliers.PresentationLayer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import suppliers.DomainLayer.*;

public class Initialize {
        public OrdersService os = OrdersService.getInstance();
        public SuppliersService ss = SuppliersService.getInstance();

        public Initialize() {
                List<Integer> l2 = new LinkedList<Integer>();
                l2.add(1);
                l2.add(4);
                ss.addSupplier("Supplier1", 1, "bank1", "payment1", new HashMap<String, String>(),
                                new LinkedList<Integer>(),
                                new HashMap<Category, HashMap<Integer, Product>>(), false, "address1");
                ss.addSupplier("Supplier2", 2, "bank2", "payment2", new HashMap<String, String>(), l2,
                                new HashMap<Category, HashMap<Integer, Product>>(), true, "address2");
                ss.addSupplier("Supplier3", 3, "bank3", "payment3", new HashMap<String, String>(),
                                new LinkedList<Integer>(),
                                new HashMap<Category, HashMap<Integer, Product>>(), true, "address3");
                ss.addSupplierContact(1, "contact1", "123456789");
                ss.addSupplierContact(1, "contact2", "987654321");
                ss.addSupplierContact(2, "contact3", "123456789");
                Category c1 = new Category("category1", 1);
                Category c2 = new Category("category2", 2);
                Product p1 = new Product("product1", 1, 1, c1, DiscountQuantity.createDiscountQuantity(1, 3, 10));
                Product p2 = new Product("product2", 2, 2, c2, DiscountQuantity.createDiscountQuantity(2, 5, 20));
                ss.addProduct(1, p1);
                ss.addProduct(2, p2);
                HashMap<Integer, Integer> items1 = new HashMap<Integer, Integer>();
                items1.put(1, 10);
                HashMap<Integer, Integer> items2 = new HashMap<Integer, Integer>();
                items2.put(2, 5);
                os.addOrder(1, "20/06/2024", items1, new LinkedList<Integer>());
                os.addOrder(2, "21/06/2024", items2, new LinkedList<Integer>());
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                Date deliveryDate = java.sql.Date.valueOf(tomorrow);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(deliveryDate);
                os.addOrder(1, formattedDate, items1, new LinkedList<Integer>());

        }

}
