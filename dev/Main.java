import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import suppliers.DomainLayer.*;
import suppliers.PresentationLayer.OrdersService;
import suppliers.PresentationLayer.SuppliersService;

public class Main {
    private final SuppliersService ss = new SuppliersService();
    private final OrdersService os = new OrdersService();

    public void menuLoop() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Suppliers");
            System.out.println("2. Orders");
            System.out.println("3. Exit");
            int choice = 0;
            try {
                choice = Integer.parseInt(System.console().readLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            switch (choice) {
                case 1:
                    suppliers();
                    break;
                case 2:
                    orders();
                    break;
                case 3:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void orders() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Order");
            System.out.println("2. Edit Order");
            System.out.println("3. Remove Order");
            System.out.println("4. Display Order");
            System.out.println("5. Display This Week's Orders");
            System.out.println("6. Back");
            int choice = 0;
            try {
                choice = Integer.parseInt(System.console().readLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            switch (choice) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    editOrder();
                    break;
                case 3:
                    removeOrder();
                    break;
                case 4:
                    displayOrder();
                    break;
                case 5:
                    displayThisWeekOrders();
                    break;
                case 6:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void editOrder() {
        boolean flag = true;
        System.out.println("Enter Order ID");
        int OrderId = Integer.parseInt(System.console().readLine());
        while (flag) {
            System.out.println("1. Edit fixed order days");
            System.out.println("2. Edit products in order");
            System.out.println("3. Edit order delivery date");
            System.out.println("4. Back");
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    editFixedOrderDays(OrderId);
                    break;
                case 2:
                    editOrderProducts(OrderId);
                    break;
                case 3:
                    editOrderDelivery(OrderId);
                    break;
                case 4:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    private void editOrderDelivery(int orderId) {
        System.out.println("Enter delivery date");
        String deliveryDate = System.console().readLine();
        System.out.println(os.editDeliveryDate(orderId, deliveryDate));
    }

    private void editOrderProducts(int orderId) {
        System.out.println(os.displayOrder(orderId));
        while (true) {
            System.out.println("Enter product catalog number to edit: (in order to stop enter -1)");
            int catalogNumber = Integer.parseInt(System.console().readLine());
            if (catalogNumber == -1)
                break;
            System.out.println("Enter product amount");
            int amount = Integer.parseInt(System.console().readLine());
            System.out.println(os.editProductInOrder(orderId, catalogNumber, amount));
        }

    }

    private void editFixedOrderDays(int orderId) {
        System.out.println(os.displayFixedOrderDays(orderId));
        System.out.println("Do you want to add or remove fixed day? - 1 (Add), 2 (Remove)");
        boolean toRemove = Integer.parseInt(System.console().readLine()) == 2;
        System.out.println("Enter fixed day (1 - Sunday to 7 - Saturday)");
        int day = Integer.parseInt(System.console().readLine());
        if (day >= 1 && day <= 7) {
            if (toRemove)
                os.removeOrderConstDeliveryDay(orderId, day);
            else
                os.addOrderConstDeliveryDay(orderId, day);
        } else {
            System.out.println("Invalid day");
        }

    }

    private void displayThisWeekOrders() {
        System.out.println(os.displayThisWeekOrders());

    }

    private void displayOrder() {
        System.out.println("Enter Order ID");
        int OrderId = Integer.parseInt(System.console().readLine());
        System.out.println(os.displayOrder(OrderId));
    }

    private void removeOrder() {
        boolean toRemove;
        System.out.println("Enter Order ID");
        int OrderId = Integer.parseInt(System.console().readLine());
        System.out.println("Are you sure you want to remove this order? - 1 (Yes), 2 (No)");
        toRemove = Integer.parseInt(System.console().readLine()) == 1;
        if (toRemove) {
            System.out.println(os.removeOrder(OrderId));
        } else {
            System.out.println("Operation Canceled");
        }
    }


    private void addOrder() {
        List<Integer> constDeliveryDays = new ArrayList<>();
        HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
        System.out.println("Enter supplier id");
        int supplierId = Integer.parseInt(System.console().readLine());
        System.out.println("Enter delivery date");
        String deliveryDate = System.console().readLine();
        System.out.println("Is this a recurring order?- 1 (Yes), 2 (No)");
        int isConstOrder = Integer.parseInt(System.console().readLine());
        if (isConstOrder == 1) {
            System.out.println("Enter number of days in a week");
            int numConstDays = Integer.parseInt(System.console().readLine());
            for (int i = 0; i < numConstDays; i++) {
                System.out.println("Enter delivery day (1 - Sunday to 7 - Saturday)");
                int day = Integer.parseInt(System.console().readLine());
                if (day >= 1 && day <= 7)
                    constDeliveryDays.add(day);
                else {
                    System.out.println("Invalid day");
                    i--;
                }
            }
        }
        System.out.println("Enter number of products in Order: ");
        int numberPro = Integer.parseInt(System.console().readLine());
        for (int j = 0; j < numberPro; j++) {
            System.out.println("Enter product catalog number");
            int catalogNumber = Integer.parseInt(System.console().readLine());
            System.out.println("Enter product amount");
            int amount = Integer.parseInt(System.console().readLine());
            items.put(catalogNumber, amount);
        }
        os.addOrder(supplierId, deliveryDate, items, constDeliveryDays);
    }

    public void suppliers() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Supplier");
            System.out.println("2. Edit Supplier");
            System.out.println("3. Remove Supplier");
            System.out.println("4. Display Supplier");
            System.out.println("5. Back");
            int choice = 0;
            try {
                choice = Integer.parseInt(System.console().readLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            switch (choice) {
                case 1:
                    addSupplier();
                    break;
                case 2:
                    editSupplier();
                    break;
                case 3:
                    removeSupplier();
                    break;
                case 4:
                    // displaySupplier();
                    break;
                case 5:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void addSupplier() {
        HashMap<String, String> contacts = new HashMap<String, String>();
        List<Integer> deliveryDays = new LinkedList<Integer>();
        HashMap<Category, HashMap<Integer, Product>> categories = new HashMap<Category, HashMap<Integer, Product>>();
        System.out.println("Enter supplier name");
        String name = System.console().readLine();
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Enter supplier bank account");
        String bankAccount = System.console().readLine();
        System.out.println("Enter supplier payment option");
        String paymentOption = System.console().readLine();
        System.out.println("Enter number of supplier contacts");
        int lim = Integer.parseInt(System.console().readLine());
        for (int i = 0; i < lim; i++) {
            System.out.println("Enter contact name");
            String contactName = System.console().readLine();
            System.out.println("Enter contact number");
            String contactNumber = System.console().readLine();
            contacts.put(contactName, contactNumber);
        }
        System.out.println("Is the supplier delivering? - 1 (Yes), 2 (No)");
        boolean isDelivering = Integer.parseInt(System.console().readLine()) == 1;
        if (isDelivering) {
            System.out.println("Enter number of supplier delivery days");
            lim = Integer.parseInt(System.console().readLine());
            for (int i = 0; i < lim; i++) {
                System.out.println("Enter delivery day (1 - Sunday to 7 - Saturday)");
                int day = Integer.parseInt(System.console().readLine());
                if (day >= 1 && day <= 7)
                    deliveryDays.add(day);
                else {
                    System.out.println("Invalid day");
                    i--;
                }

            }
        }
        System.out.println("Enter number of supplier categories");
        lim = Integer.parseInt(System.console().readLine());
        for (int i = 0; i < lim; i++) {
            System.out.println("Enter category name");
            String categoryName = System.console().readLine();
            System.out.println("Enter category ID");
            int categoryID = Integer.parseInt(System.console().readLine());
            Category category = new Category(categoryName, categoryID);
            HashMap<Integer, Product> products = new HashMap<Integer, Product>();
            System.out.println("Enter number of products in category: " + categoryName);
            int lim2 = Integer.parseInt(System.console().readLine());
            for (int j = 0; j < lim2; j++) {
                System.out.println("Enter product catalog number");
                int catalogNumber = Integer.parseInt(System.console().readLine());
                System.out.println("Enter product name");
                String productName = System.console().readLine();
                System.out.println("Enter product price");
                double productPrice = Double.parseDouble(System.console().readLine());
                System.out.println("Enter product discount precentage");
                double discountPercentage = Double.parseDouble(System.console().readLine());
                System.out.println("Enter product discount amount");
                int discountAmount = Integer.parseInt(System.console().readLine());
                DiscountQuantity dq = new DiscountQuantity(catalogNumber, discountAmount, productPrice,
                        discountPercentage);
                //   Product product = new Product(productName, catalogNumber, productPrice, id, category, dq);
                //    products.put(catalogNumber, product);
            }
            categories.put(category, products);
        }
        System.out.println(
                ss.addSupplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories, isDelivering));
    }

    private Product createProductObj(int supplierID, Category category) {
        System.out.println("Enter product catalog number");
        int catalogNumber = Integer.parseInt(System.console().readLine());
        System.out.println("Enter product name");
        String productName = System.console().readLine();
        System.out.println("Enter product price");
        double productPrice = Double.parseDouble(System.console().readLine());
        System.out.println("Enter product discount precentage");
        double discountPercentage = Double.parseDouble(System.console().readLine());
        System.out.println("Enter product discount amount");
        int discountAmount = Integer.parseInt(System.console().readLine());
        DiscountQuantity dq = new DiscountQuantity(catalogNumber, discountAmount, productPrice,
                discountPercentage);
        //  Product product = new Product(productName, catalogNumber, productPrice, supplierID, category, dq);
        return null;
    }

    public void editSupplier() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Edit supplier card");
            System.out.println("2. Edit supplier products");
            System.out.println("3. Back");
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    editSupplierCard();
                    break;
                case 2:
                    editSupplierProducts();
                    break;
                case 3:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void editSupplierCard() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Edit supplier name");
            System.out.println("2. Edit supplier bank account");
            System.out.println("3. Edit supplier payment option");
            System.out.println("4. Edit supplier contacts");
            System.out.println("5. Back");
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    editSupplierName();
                    break;
                case 2:
                    editSupplierBankAccount();
                    break;
                case 3:
                    editSupplierPaymentOption();
                    break;
                case 4:
                    editSupplierContacts();
                    break;
                case 5:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    public void editSupplierName() {
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Enter new supplier name");
        String name = System.console().readLine();
        System.out.println(ss.setSupplierName(id, name));
    }

    public void editSupplierBankAccount() {
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Enter new supplier bank account");
        String bankAccount = System.console().readLine();
        System.out.println(ss.setSupplierBankAccount(id, bankAccount));
    }

    public void editSupplierPaymentOption() {
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Enter new supplier payment option");
        String paymentOption = System.console().readLine();
        System.out.println(ss.setSupplierPaymentOption(id, paymentOption));
    }

    public void editSupplierContacts() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add contact");
            System.out.println("2. Remove contact");
            System.out.println("3. Back");
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    removeContact();
                    break;
                case 3:
                    flag = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void editSupplierProducts() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Product");
            System.out.println("2. Edit Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Back");
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    //     editProduct();
                    break;
                case 3:
                    //      removeProduct();
                    break;
                case 4:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void addProduct() {

    }

    public void addContact() {
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Enter contact name");
        String contactName = System.console().readLine();
        System.out.println("Enter contact details");
        String contactNumber = System.console().readLine();
        System.out.println(ss.addSupplierContact(id, contactName, contactNumber));
    }

    public void removeContact() {
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Enter contact name");
        String contactName = System.console().readLine();
        System.out.println(ss.removeSupplierContact(id, contactName));
    }

    public void removeSupplier() {
        System.out.println("Enter supplier ID");
        int id = Integer.parseInt(System.console().readLine());
        System.out.println("Are you sure you want to remove supplier " + id + "? - 1 (Yes), 2 (No)");
        boolean toRemove = Integer.parseInt(System.console().readLine()) == 1;
        if (toRemove) {
            System.out.println(ss.removeSupplier(id));
        } else {
            System.out.println("Operation Canceled");
        }
    }


    public void main(String[] args) {
        menuLoop();
    }
}