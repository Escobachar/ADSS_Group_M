package suppliers.PresentationLayer;

import java.util.HashMap;
import java.util.List;

import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.OrdersFacade;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.SuppliersFacade;

public class SuppliersService {
    private SuppliersFacade sf;

    private static SuppliersService instance;

    private SuppliersService() {
        sf = SuppliersFacade.getInstance();

    }

    public static SuppliersService getInstance() {
        if (instance == null) {
            instance = new SuppliersService();
        }
        return instance;
    }

    public String addSupplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering, String address) {
        try {
            sf.addSupplier(name, id, bankAccount, paymentOption, contacts, deliveryDays,
                    categories, isDelivering, address);
            return "Supplier added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setSupplierName(int id, String name) {
        try {
            sf.setSupplierName(id, name);
            return "Supplier name updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setSupplierBankAccount(int id, String bankAccount) {
        try {
            sf.setSupplierBankAccount(id, bankAccount);
            return "Supplier bank account updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setSupplierPaymentOption(int id, String paymentOption) {
        try {
            sf.setSupplierPaymentOption(id, paymentOption);
            return "Supplier payment option updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addSupplierContact(int id, String contactName, String contactValue) {
        try {
            sf.addSupplierContact(id, contactName, contactValue);
            return "Supplier contact added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeSupplierContact(int id, String contactName) {
        try {
            sf.removeSupplierContact(id, contactName);
            return "Supplier contact removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeSupplier(int id) {
        try {
            sf.removeSupplier(id);
            return "Supplier removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addProduct(int id, Product product) {
        try {
            sf.addProductToSupplier(id, product);
            return "Product added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setCatalogNumber(int newCatalogNumber, int supplierId, int catalogNumber) {
        try {
            sf.setCatalogNumber(newCatalogNumber, supplierId, catalogNumber);
            return "Catalog number updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setDiscountAmount(int newDiscountAmount, int supplierId, int catalogNumber) {
        try {
            sf.setDiscountAmount(newDiscountAmount, supplierId, catalogNumber);
            return "Discount amount updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setDiscountPrecentage(double newDiscount, int supplierId, int catalogNumber) {
        try {
            sf.setDiscountPrecentage(newDiscount, supplierId, catalogNumber);
            return "Discount percentage updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setProductName(String newName, int supplierId, int catalogNumber) {
        try {
            sf.setProductName(newName, supplierId, catalogNumber);
            return "Product name updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setProductPrice(double newPrice, int supplierId, int catalogNumber) {
        try {
            sf.setPrice(newPrice, supplierId, catalogNumber);
            return "Price updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeProduct(int supplierId, int catalogNumber) {
        try {
            sf.removeProductFromSupplier(supplierId, catalogNumber);
            return "Product removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displaySupplier(int id) {
        try {
            return sf.getSupplier(id).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displaySupplierCard(int id) {
        try {
            return sf.getSupplier(id).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displayProducts(int id) {
        try {
            HashMap<Integer, Product> products = sf.getAllSupplierProducts(id);
            if (products.isEmpty())
                return "No products have been added yet";
            String result = "";
            for (Product product : products.values()) {
                result += product.toString() + "\n";
            }
            return result;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displayPurchasedProducts(int id) {
        try {
            List<Product> products = sf.getPurchasedProductsFromSupplier(id);
            String result = "";
            for (Product product : products) {
                result += product.toString() + "\n";
            }
            if (result.equals(""))
                return "No products have been purchased yet";
            return result;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String supplierExists(int id) {
        try {
            sf.getSupplier(id);
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}