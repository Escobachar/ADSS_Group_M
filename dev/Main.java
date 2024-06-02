import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.DiscountQuantity;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Supplier;
import suppliers.PresentationLayer.OrdersService;
import suppliers.PresentationLayer.SuppliersService;

public class Main {

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

    public void suppliers() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Add Supplier");
            System.out.println("2. Edit Supplier");
            System.out.println("3. Delete Supplier");
            System.out.println("4. Edit Supplier");
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
                    deleteSupplier();
                    break;
                case 4:
                    editSupplier();
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
            Category category = new Category(categoryID, categoryName);
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
                Product product = new Product(productName, catalogNumber, productPrice, id, category, dq);
                products.put(catalogNumber, product);
            }
            categories.put(category, products);
        }
        ss.addSupplier(name, id, bankAccount, paymentOption, contacts, deliveryDays, categories, isDelivering);
    }

    public static void main(String[] args) {
        SuppliersService ss = new SuppliersService();

    }
}