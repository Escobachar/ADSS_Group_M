package suppliers.PresentationLayer;

import java.util.HashMap;
import java.util.List;

import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.SuppliersFacade;

public class SuppliersService {

    public String addSupplier(String name, int id, String bankAccount, String paymentOption,
            HashMap<String, String> contacts, List<Integer> deliveryDays,
            HashMap<Category, HashMap<Integer, Product>> categories, boolean isDelivering, String address) {
        try {
            SuppliersFacade.getInstance().addSupplier(name, id, bankAccount, paymentOption, contacts, deliveryDays,
                    categories, isDelivering, address);
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

    public String addProduct(int id, Product product) {
        try {
            SuppliersFacade.getInstance().addProductToSupplier(id, product);
            return "Product added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setCatalogNumber(int newCatalogNumber, int supplierId, int catalogNumber) {
        try {
            SuppliersFacade.getInstance().setCatalogNumber(newCatalogNumber, supplierId, catalogNumber);
            return "Catalog number updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setDiscountAmount(int newDiscountAmount, int supplierId, int catalogNumber) {
        try {
            SuppliersFacade.getInstance().setDiscountAmount(newDiscountAmount, supplierId, catalogNumber);
            return "Discount amount updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setDiscountPrecentage(double newDiscount, int supplierId, int catalogNumber) {
        try {
            SuppliersFacade.getInstance().setDiscountPrecentage(newDiscount, supplierId, catalogNumber);
            return "Discount percentage updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setProductName(String newName, int supplierId, int catalogNumber) {
        try {
            SuppliersFacade.getInstance().setProductName(newName, supplierId, catalogNumber);
            return "Product name updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String setProductPrice(double newPrice, int supplierId, int catalogNumber) {
        try {
            SuppliersFacade.getInstance().setPrice(newPrice, supplierId, catalogNumber);
            return "Price updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String removeProduct(int supplierId, int catalogNumber) {
        try {
            SuppliersFacade.getInstance().removeProductFromSupplier(supplierId, catalogNumber);
            return "Product removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displaySupplier(int id) {
        try {
            return SuppliersFacade.getInstance().getSupplier(id).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displaySupplierCard(int id) {
        try {
            return SuppliersFacade.getInstance().getSupplier(id).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String displayProducts(int id) {
        try {
            HashMap<Integer, Product> products = SuppliersFacade.getInstance().getAllSupplierProducts(id);
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
            List<Product> products = SuppliersFacade.getInstance().getPurchasedProductsFromSupplier(id);
            String result = "";
            for (Product product : products) {
                result += product.toString() + "\n";
            }
            return result;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

}