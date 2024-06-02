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
    private SuppliersService ss = new SuppliersService();

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
                    displaySupplier();
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
        Product product = new Product(productName, catalogNumber, productPrice, supplierID, category, dq);

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
                    editProduct();
                    break;
                case 3:
                    removeProduct();
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
        boolean isDelivering = Integer.parseInt(System.console().readLine()) == 1;
        if (isDelivering) {
            System.out.println(ss.removeSupplier(id));
        } else {
            System.out.println("Operation Canceled");
        }
    }

}

public static void main(String[] args) {

}