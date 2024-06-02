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

    public String setSupplierName(int id, String name) {
        try {
            SuppliersFacade.getInstance().setSupplierName(id, name);
            return "Supplier name updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setSupplierBankAccount(int id, String bankAccount) {
        try {
            SuppliersFacade.getInstance().setSupplierBankAccount(id, bankAccount);
            return "Supplier bank account updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setSupplierPaymentOption(int id, String paymentOption) {
        try {
            SuppliersFacade.getInstance().setSupplierPaymentOption(id, paymentOption);
            return "Supplier payment option updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addSupplierContact(int id, String contactName, String contactValue) {
        try {
            SuppliersFacade.getInstance().addSupplierContact(id, contactName, contactValue);
            return "Supplier contact added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeSupplierContact(int id, String contactName) {
        try {
            SuppliersFacade.getInstance().removeSupplierContact(id, contactName);
            return "Supplier contact removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeSupplier(int id) {
        try {
            SuppliersFacade.getInstance().removeSupplier(id);
            return "Supplier removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
