package Suppliers.PresentationLayer;


import Suppliers.DomainLayer.Category;
import Suppliers.DomainLayer.DiscountQuantity;
import Suppliers.DomainLayer.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SuppliersMain {
    static Scanner sc = new Scanner(System.in);
    private static final SuppliersService ss = SuppliersService.getInstance();
    private static final OrdersService os = OrdersService.getInstance();
    private static boolean retrieveData = false;

    private static Integer inputToInt() {
        String line = sc.next();
        try {
            return Integer.parseInt(line);
        } catch (Exception e) {
            return null;
        }
    }

    public static void menuLoop(String branch) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Suppliers");
            System.out.println("2. Orders");
            System.out.println("3. Back");
            Integer choice = inputToInt();
            if (choice == null)
                System.out.println("Invalid choice");
            else {
                switch (choice) {
                    case 1 -> suppliers();
                    case 2 -> orders(branch);
                    case 3 -> flag = false;
                    default -> System.out.println("Invalid choice");
                }
            }
        }
    }

    private static void orders(String branch) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Order");
            System.out.println("2. Edit Order");
            System.out.println("3. Remove Order");
            System.out.println("4. Display Order");
            System.out.println("5. Display This Week's Orders");
            System.out.println("6. Display this week deliveries");
            System.out.println("7. Add Orders Based On Stock Shortages");
            System.out.println("8. Back");
            Integer choice = inputToInt();
            if (choice == null)
                System.out.println("Invalid choice");
            else {
                switch (choice) {
                    case 1 -> addOrder(branch);
                    case 2 -> editOrder();
                    case 3 -> removeOrder();
                    case 4 -> displayOrder();
                    case 5 -> displayThisWeekOrders();
                    case 6 -> displayThisWeekDeliveries();
                    case 7 -> addOrdersBasedOnStockShortages(branch);
                    case 8 -> flag = false;
                    default -> System.out.println("Invalid choice");
                }
            }
        }
    }

    private static void addOrdersBasedOnStockShortages(String branch) {
        HashMap<String, Integer> productToOrder = new HashMap<>();
        boolean flag = true;
        while (flag) {
            System.out.println("Enter product name: (Enter 0 to stop)");
            String name = sc.next();
            if (name.equals("0")) {
                flag = false;
                break;
            }
            if (!ss.isProductExists(name)) {
                System.out.println("The product is not available at any supplier");
            } else {
                System.out.println("Enter product amount");
                Integer amount = inputToInt();
                if (amount == null) {
                    System.out.println("Invalid amount");
                } else {
                    productToOrder.put(name, amount);
                }
            }
        }
        HashMap<Integer, HashMap<Product, Integer>> supplierToOrder = ss.getCheapestProducts(productToOrder);
        if (supplierToOrder.isEmpty()) {
            System.out.println("Operation Canceled");
            return;
        }
        String supplierToOrderString = "";
        for (Map.Entry<Integer, HashMap<Product, Integer>> sup : supplierToOrder.entrySet()) {
            supplierToOrderString += "Supplier ID: " + sup.getKey().toString() + ":\n";
            for (Map.Entry<Product, Integer> products : sup.getValue().entrySet()) {
                supplierToOrderString += products.getKey().productToString(products.getValue()) + "\n";
            }
        }
        System.out.println("These are the suppliers with the lowest price for the out-of-stock products");
        System.out.println(supplierToOrderString);
        System.out.println("Would you like to add these orders? - 1 (Yes), 2 (No)");
        Integer choice = inputToInt();
        boolean toRemove = choice != null && choice == 1;
        if (toRemove) {
            addingOrders(supplierToOrder, branch);
        } else {
            System.out.println("Operation Canceled");
        }

    }

    private static void addingOrders(HashMap<Integer, HashMap<Product, Integer>> supplierToOrder, String branch) {
        System.out.println("Enter delivery date (dd/MM/yyyy)");
        String deliveryDate = sc.next();
        for (Map.Entry<Integer, HashMap<Product, Integer>> sup : supplierToOrder.entrySet()) {
            HashMap<Integer, Integer> items = new HashMap<>();
            for (Map.Entry<Product, Integer> products : sup.getValue().entrySet()) {
                items.put(products.getKey().getCatalogNumber(), products.getValue());
            }
            System.out.println(os.addOrder(sup.getKey(), deliveryDate, items, null, branch));
        }
    }

    private static void editOrder() {
        boolean flag = true;
        System.out.println("Enter Order ID");
        Integer OrderId = inputToInt();
        if (OrderId == null) {
            System.out.println("Invalid choice");
            return;
        }
        if (!os.isOrderExists(OrderId)) {
            System.out.println("Order Doesn't Exists");
        }
        if (!os.isOrderCanBeEdit(OrderId)) {
            System.out.println("It is not possible to change an order whose delivery date is within a day");
            return;
        }
        while (flag) {
            System.out.println("1. Edit fixed order days");
            System.out.println("2. Edit products in order");
            System.out.println("3. Edit order delivery date");
            System.out.println("4. Back");
            Integer choice = inputToInt();
            if (choice == null)
                System.out.println("Invalid choice");
            else {
                switch (choice) {
                    case 1 -> editFixedOrderDays(OrderId);
                    case 2 -> editOrderProducts(OrderId);
                    case 3 -> editOrderDelivery(OrderId);
                    case 4 -> flag = false;
                    default -> System.out.println("Invalid choice");
                }

            }
        }

    }

    private static void editOrderDelivery(int orderId) {
        System.out.println("Enter delivery date (dd/MM/yyyy)");
        String deliveryDate = sc.next();
        System.out.println(os.editDeliveryDate(orderId, deliveryDate));
    }

    private static void editOrderProducts(int orderId) {
        System.out.println(os.displayOrder(orderId));
        while (true) {
            System.out.println("Enter product catalog number to edit: (in order to stop enter -1)");
            Integer catalogNumber = inputToInt();
            if (catalogNumber == null) {
                System.out.println("Invalid choice");
                return;
            }
            if (catalogNumber == -1)
                break;
            System.out.println("Enter product amount: (in order to remove product enter 0)");
            String sChoice = sc.next();
            int amount = Integer.parseInt(sChoice);
            System.out.println(os.editProductInOrder(orderId, catalogNumber, amount));

        }
    }

    private static void editFixedOrderDays(int orderId) {
        System.out.println(os.displayFixedOrderDays(orderId));
        System.out.println("Do you want to add or remove fixed day? - 1 (Add), 2 (Remove)");
        try {
            Integer choice = inputToInt();
            boolean toRemove = choice != null && choice == 2;
            System.out.println("Enter fixed day (1 - Sunday to 7 - Saturday)");
            Integer day = inputToInt();
            if (day == null) {
                System.out.println("Invalid day");
                return;
            }
            if (day >= 1 && day <= 7) {
                if (toRemove)
                    System.out.println(os.removeOrderConstDeliveryDay(orderId, day));
                else
                    System.out.println(os.addOrderConstDeliveryDay(orderId, day));
            } else {
                System.out.println("Invalid day");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayThisWeekOrders() {
        System.out.println(os.displayThisWeekOrders());

    }

    private static void displayOrder() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Display order");
            System.out.println("2. Display order price");
            System.out.println("3. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> printOrder();
                case 2 -> printOrderPrice();
                case 3 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void printOrderPrice() {
        System.out.println("Enter Order ID");
        Integer OrderId = inputToInt();
        if (OrderId == null)
            System.out.println("Invalid choice");
        else {
            System.out.println(os.displayOrderPrice(OrderId));
        }
    }

    private static void printOrder() {
        System.out.println("Enter Order ID");
        Integer OrderId = inputToInt();
        if (OrderId == null)
            System.out.println("Invalid choice");
        else {
            System.out.println(os.displayOrder(OrderId));
        }
    }

    private static void removeOrder() {
        boolean toRemove;
        System.out.println("Enter Order ID");
        Integer OrderId = inputToInt();
        if (OrderId == null)
            System.out.println("Invalid choice");
        else {
            if (!os.isOrderExists(OrderId)) {
                System.out.println("Order Doesn't Exists");
            }
            if (!os.isOrderCanBeEdit(OrderId)) {
                System.out.println("It is not possible to remove an order whose delivery date is within a day");
                return;
            }
            System.out.println("Are you sure you want to remove this order? - 1 (Yes), 2 (No)");
            Integer choice = inputToInt();
            toRemove = choice != null && choice == 1;
            if (toRemove) {
                System.out.println(os.removeOrder(OrderId));
            } else {
                System.out.println("Operation Canceled");
            }
        }
    }

    private static void addOrder(String branch) {
        List<Integer> constDeliveryDays = new ArrayList<>();
        HashMap<Integer, Integer> items = new HashMap<>();
        Integer supplierId;
        do {
            System.out.println("Enter supplier id");
            supplierId = inputToInt();
        } while (supplierId == null || !ss.isSupplierExists(supplierId));
        System.out.println("Enter delivery date (dd/MM/yyyy)");
        String deliveryDate = sc.next();
        System.out.println("Is this a recurring order?- 1 (Yes), 2 (No)");
        Integer isConstOrder = inputToInt();
        if (isConstOrder == null) {
            System.out.println("Invalid number");
            return;
        }
        if (isConstOrder == 1) {
            System.out.println("Enter number of days in a week");
            Integer numConstDays = inputToInt();
            if (numConstDays == null) {
                System.out.println("Invalid number of days");
                return;
            }
            for (int i = 0; i < numConstDays; i++) {
                System.out.println("Enter delivery day (1 - Sunday to 7 - Saturday)");
                Integer day = inputToInt();
                if (day == null) {
                    System.out.println("Invalid day");
                    return;
                }
                if (day >= 1 && day <= 7)
                    constDeliveryDays.add(day);
                else {
                    System.out.println("Invalid day");
                    i--;
                }
            }
        }
        System.out.println("Enter number of products in Order: ");
        Integer numberPro = inputToInt();
        if (numberPro == null) {
            System.out.println("Invalid day");
            return;
        }
        for (int j = 0; j < numberPro; j++) {
            System.out.println("Enter product catalog number");
            Integer catalogNumber = inputToInt();
            if (catalogNumber == null) {
                System.out.println("Invalid catalog number");
                return;
            }
            if (!ss.isProductExistsInSupplier(supplierId, null, catalogNumber)) {
                System.out.println("Product Doesn't Exists in Supplier " + supplierId);
            } else {
                System.out.println("Enter product amount");
                Integer amount = inputToInt();
                if (amount == null) {
                    System.out.println("Invalid amount");
                    return;
                }
                items.put(catalogNumber, amount);
            }
        }
        System.out.println(os.addOrder(supplierId, deliveryDate, items, constDeliveryDays, branch));
    }

    public static void suppliers() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Supplier");
            System.out.println("2. Edit Supplier");
            System.out.println("3. Remove Supplier");
            System.out.println("4. Display Supplier");
            System.out.println("5. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> addSupplier();
                case 2 -> editSupplier();
                case 3 -> removeSupplier();
                case 4 -> displaySupplier();
                case 5 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void addSupplier() {
        HashMap<String, String> contacts = new HashMap<>();
        List<Integer> deliveryDays = new LinkedList<>();
        HashMap<Category, HashMap<Integer, Product>> categories = new HashMap<>();
        System.out.println("Enter supplier name:");
        String name = sc.next();
        System.out.println("Enter supplier ID:");
        Integer id = inputToInt();
        if (id == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Enter supplier bank account:");
        String bankAccount = sc.next();
        System.out.println("Enter supplier payment option:");
        String paymentOption = sc.next();
        System.out.println("Enter supplier address:");
        String address = sc.next();
        System.out.println("Enter number of supplier contacts:");
        Integer lim = inputToInt();
        if (lim == null) {
            System.out.println("Invalid number");
            return;
        }
        for (int i = 0; i < lim; i++) {
            System.out.println("Enter contact name");
            String contactName = sc.next();
            System.out.println("Enter contact number");
            String contactNumber = sc.next();
            contacts.put(contactName, contactNumber);
        }
        System.out.println("Is the supplier delivering? - 1 (Yes), 2 (No)");
        Integer choice = inputToInt();
        boolean isDelivering = choice != null && choice == 1;
        if (isDelivering) {
            System.out.println("Enter number of supplier delivery days");
            Integer limD = inputToInt();
            if (limD == null) {
                System.out.println("Invalid number");
                return;
            }
            for (int i = 0; i < limD; i++) {
                System.out.println("Enter delivery day (1 - Sunday to 7 - Saturday)");
                Integer day = inputToInt();
                if (day == null) {
                    System.out.println("Invalid number");
                    return;
                }
                if (day >= 1 && day <= 7)
                    deliveryDays.add(day);
                else {
                    System.out.println("Invalid day");
                    i--;
                }

            }
        }
        System.out.println("Enter number of supplier categories");
        lim = inputToInt();
        if (lim == null) {
            System.out.println("Invalid number");
            return;
        }
        for (int i = 0; i < lim; i++) {
            System.out.println("Enter category name");
            String categoryName = sc.next();
            System.out.println("Enter category ID");
            Integer categoryID = inputToInt();
            if (categoryID == null) {
                System.out.println("Invalid number");
                return;
            }
            Category category = new Category(categoryName, categoryID);
            HashMap<Integer, Product> products = new HashMap<>();
            System.out.println("Enter number of products in category: " + categoryName);
            Integer limP = inputToInt();
            if (limP == null) {
                System.out.println("Invalid number");
                return;
            }
            for (int j = 0; j < limP; j++) {
                Product product = createProduct(id, category);
                if (product != null)
                    products.put(product.getCatalogNumber(), product);
            }
            categories.put(category, products);
        }
        System.out.println(
                ss.addSupplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories, isDelivering,
                        address));
    }

    public static void displaySupplier() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Display supplier card");
            System.out.println("2. Display supplier products");
            System.out.println("3. Display purchased supplier products");
            System.out.println("4. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> displaySupplierCard();
                case 2 -> displaySupplierProducts();
                case 3 -> displayBoughtSupplierProducts();
                case 4 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void displaySupplierProducts() {
        System.out.println("Enter supplier ID");
        Integer id = inputToInt();
        if (id == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println(ss.displayProducts(id));
    }

    public static void displayBoughtSupplierProducts() {
        System.out.println("Enter supplier ID");
        Integer id = inputToInt();
        if (id == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println(ss.displayPurchasedProducts(id));
    }

    public static void displaySupplierCard() {
        System.out.println("Enter supplier ID");
        Integer id = inputToInt();
        if (id == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println(ss.displaySupplierCard(id));
    }

    private static Product createProduct(int sID, Category category) {
        System.out.println("Enter product catalog number");
        Integer catalogNumber = inputToInt();
        if (catalogNumber == null) {
            System.out.println("Invalid number");
            return null;
        }
        if (ss.isSupplierExists(sID) && ss.isProductExistsInSupplier(sID, category, catalogNumber)) {
            System.out.println("Product Already Exists");
            return null;
        }
        System.out.println("Enter product name");
        String productName = sc.next();
        System.out.println("Enter product price");
        try {
            double productPrice = Double.parseDouble(sc.next());
            System.out.println("Enter product discount percentage");
            double discountPercentage = Double.parseDouble(sc.next());
            System.out.println("Enter product discount amount");
            Integer discountAmount = inputToInt();
            if (discountAmount == null) {
                System.out.println("Invalid number");
                return null;
            }
            DiscountQuantity dq = DiscountQuantity.createDiscountQuantity(catalogNumber, discountAmount,
                    discountPercentage);

            return new Product(productName, catalogNumber, productPrice, category, dq);
        } catch (Exception e) {
            System.out.println("Invalid number");
            return null;
        }
    }

    public static void editSupplier() {
        System.out.println("Enter supplier ID");
        Integer id = inputToInt();
        if (id == null) {
            System.out.println("Invalid number");
            return;
        }
        String isExists = ss.supplierExists(id);
        if (isExists.equals("")) {
            boolean flag = true;
            while (flag) {
                System.out.println("1. Edit supplier card");
                System.out.println("2. Edit supplier products");
                System.out.println("3. Back");
                Integer choice = inputToInt();
                if (choice == null) {
                    System.out.println("Invalid number");
                    return;
                }
                switch (choice) {
                    case 1 -> editSupplierCard(id);
                    case 2 -> editSupplierProducts(id);
                    case 3 -> flag = false;
                    default -> System.out.println("Invalid choice");
                }
            }
        } else {
            System.out.println(isExists);
        }
    }

    public static void editSupplierCard(int id) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Edit supplier name");
            System.out.println("2. Edit supplier bank account");
            System.out.println("3. Edit supplier payment option");
            System.out.println("4. Edit supplier contacts");
            System.out.println("5. Edit supplier address");
            System.out.println("6. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> editSupplierName(id);
                case 2 -> editSupplierBankAccount(id);
                case 3 -> editSupplierPaymentOption(id);
                case 4 -> editSupplierContacts(id);
                case 5 -> editSupplierAddress(id);
                case 6 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }

    }

    public static void editSupplierName(int id) {
        System.out.println("Enter new supplier name");
        String name = sc.next();
        System.out.println(ss.setSupplierName(id, name));
    }

    public static void editSupplierAddress(int id) {
        System.out.println("Enter new supplier address");
        String address = sc.next();
        System.out.println(ss.setSupplierAddress(id, address));
    }

    public static void editSupplierBankAccount(int id) {
        System.out.println("Enter new supplier bank account");
        String bankAccount = sc.next();
        System.out.println(ss.setSupplierBankAccount(id, bankAccount));
    }

    public static void editSupplierPaymentOption(int id) {
        System.out.println("Enter new supplier payment option");
        String paymentOption = sc.next();
        System.out.println(ss.setSupplierPaymentOption(id, paymentOption));
    }

    public static void editSupplierContacts(int id) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add contact");
            System.out.println("2. Remove contact");
            System.out.println("3. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> addContact(id);
                case 2 -> removeContact(id);
                case 3 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void editSupplierProducts(int id) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Product");
            System.out.println("2. Edit Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> addProduct(id);
                case 2 -> editProduct(id);
                case 3 -> removeProduct(id);
                case 4 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void removeProduct(int id) {
        System.out.println("Enter Catalog Number");
        Integer catalogNumber = inputToInt();
        if (catalogNumber == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Are you sure you want to remove product " + catalogNumber + "? - 1 (Yes), 2 (No)");
        Integer choice = inputToInt();
        boolean toRemove = choice != null && choice == 1;
        if (toRemove)
            System.out.println(ss.removeProduct(id, catalogNumber));
        else
            System.out.println("Canceled");
    }

    public static void addProduct(int id) {
        System.out.println("Enter category name");
        String categoryName = sc.next();
        System.out.println("Enter category ID");
        Integer categoryID = inputToInt();
        if (categoryID == null) {
            System.out.println("Invalid number");
            return;
        }
        Category category = new Category(categoryName, categoryID);
        Product product = createProduct(id, category);
        if (product != null)
            System.out.println(ss.addProduct(id, product));
    }

    public static void editProduct(int id) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Change Product Discount Amount");
            System.out.println("2. Change Product Discount Percentage");
            System.out.println("3. Change Product Name");
            System.out.println("4. Change Product Price");
            System.out.println("5. Back");
            Integer choice = inputToInt();
            if (choice == null) {
                System.out.println("Invalid number");
                return;
            }
            switch (choice) {
                case 1 -> setDiscountAmount(id);
                case 2 -> setDiscountPercentage(id);
                case 3 -> setProductName(id);
                case 4 -> setProductPrice(id);
                case 5 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void setDiscountAmount(int id) {
        System.out.println("Enter Catalog Number");
        Integer catalogNumber = inputToInt();
        if (catalogNumber == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Enter new discount amount");
        Integer discountAmount = inputToInt();
        if (discountAmount == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println(ss.setDiscountAmount(discountAmount, id, catalogNumber));
    }

    public static void setDiscountPercentage(int id) {
        System.out.println("Enter Catalog Number");
        Integer catalogNumber = inputToInt();
        if (catalogNumber == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Enter new discount percentage");
        double discountPercentage = Double.parseDouble(sc.next());
        System.out.println(ss.setDiscountPercentage(discountPercentage, id, catalogNumber));
    }

    public static void setProductName(int id) {
        System.out.println("Enter Catalog Number");
        Integer catalogNumber = inputToInt();
        if (catalogNumber == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Enter new product name");
        String name = sc.next();
        System.out.println(ss.setProductName(name, id, catalogNumber));
    }

    public static void setProductPrice(int id) {
        System.out.println("Enter Catalog Number");
        Integer catalogNumber = inputToInt();
        if (catalogNumber == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Enter new product price");
        double price = Double.parseDouble(sc.next());
        System.out.println(ss.setProductPrice(price, id, catalogNumber));
    }

    public static void addContact(int id) {
        System.out.println("Enter contact name");
        String contactName = sc.next();
        System.out.println("Enter contact details");
        String contactNumber = sc.next();
        System.out.println(ss.addSupplierContact(id, contactName, contactNumber));
    }

    public static void removeContact(int id) {
        System.out.println("Enter contact name");
        String contactName = sc.next();
        System.out.println(ss.removeSupplierContact(id, contactName));
    }

    public static void removeSupplier() {
        System.out.println("Enter supplier ID");
        Integer id = inputToInt();
        if (id == null) {
            System.out.println("Invalid number");
            return;
        }
        System.out.println("Are you sure you want to remove supplier " + id + "? - 1 (Yes), 2 (No)");
        Integer choice = inputToInt();
        boolean toRemove = choice != null && choice == 1;
        if (toRemove) {
            System.out.println(ss.removeSupplier(id));
        } else {
            System.out.println("Operation Canceled");
        }
    }

    public static void displayThisWeekDeliveries() {
        if (os.displayThisWeekOrders().isEmpty())
            System.out.println("No orders this week");
        else
            System.out.println(os.displayThisWeekDeliveries());
    }

    private static void retrieveData() {
        if (retrieveData)
            return;
        SuppliersService ss = SuppliersService.getInstance();
        OrdersService os = OrdersService.getInstance();
        ss.retrieveData();
        os.retrieveData();
        retrieveData = true;
    }

    public static void suppliersMain(String branch) {
        retrieveData();
        menuLoop(branch);
    }
}