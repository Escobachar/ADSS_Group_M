import org.junit.Test;

import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Supplier;
import suppliers.DomainLayer.SuppliersFacade;
import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.DiscountQuantity;

import static org.junit.Assert.*;

import org.junit.Before;

public class SuppliersFacadeTest {
    private SuppliersFacade suppliersFacade;
    private Supplier supplier;
    private Product productMilk;
    private Category categoryDairy;
    private DiscountQuantity discountQuantity;

    @Before
    public void setUp() {
        suppliersFacade = new SuppliersFacade();
        supplier = new Supplier("Shimon", 1, "123", "check", false);
        suppliersFacade.addSupplier(supplier);
        categoryDairy = new Category("Dairy", 0);
        discountQuantity = new DiscountQuantity(1, 10, 5.9, 5);
        productMilk = new Product("milk", 1, 5.9, supplier.getId(), categoryDairy, discountQuantity, 0);
        // Add the product to the supplier
        suppliersFacade.addProductToSupplier(supplier.getId(), productMilk);

    }

    @Test
    public void testGetProductInSupplier() {
        Product product = suppliersFacade.getProductInSupplier(1, categoryDairy, productMilk.getCatalogNumber());
        assertNotNull("Product should not be null", product);
        assertEquals("milk", product.getName());
        assertEquals(5.9, product.getPrice(), 0.00);
        assertEquals(categoryDairy, product.getCategory());
        assertEquals(discountQuantity.getAmount(), product.getDiscount().getAmount());
    }

    @Test
    public void testEditProductInSupplier() {
        suppliersFacade.setPrice(6.9, supplier.getId(), productMilk.getCatalogNumber());
        suppliersFacade.setDiscountAmount(3, supplier.getId(), productMilk.getCatalogNumber());
        suppliersFacade.setDiscountPrecentage(0.2, supplier.getId(), productMilk.getCatalogNumber());
        suppliersFacade.setCatalogNumber(2, supplier.getId(), productMilk.getCatalogNumber());
        suppliersFacade.setProductName("soy milk", supplier.getId(), productMilk.getCatalogNumber());
        Product editedProduct = suppliersFacade.getProductInSupplier(supplier.getId(), categoryDairy,
                productMilk.getCatalogNumber());

        assertEquals("soy milk", editedProduct.getName());
        assertEquals(6.9, editedProduct.getPrice(), 0.00); // Use delta value to account for floating-point precision
        assertEquals(2, editedProduct.getCatalogNumber());
        assertEquals(3, editedProduct.getDiscount().getAmount());
        assertEquals(0.2, editedProduct.getDiscount().getDiscountPrecentage(), 0.00);
    }

    @Test
    public void testRemoveProductFromSupplier() {
        suppliersFacade.removeProductFromSupplier(supplier.getId(), productMilk.getCatalogNumber());
        try {
            suppliersFacade.getProductInSupplier(supplier.getId(), categoryDairy, productMilk.getCatalogNumber());
            fail("Product should not be found");
        } catch (IllegalArgumentException e) {
            assertEquals("Product " + productMilk.getCatalogNumber() + " not found in supplier " + supplier.getId(),
                    e.getMessage());
        }
    }

    @Test
    public void removeSupplier() {
        suppliersFacade.removeSupplier(supplier.getId());
        try {
            suppliersFacade.getSupplier(supplier.getId());
            fail("Supplier should not be found");
        } catch (IllegalArgumentException e) {
            assertEquals("Supplier with ID " + supplier.getId() + " not found", e.getMessage());
        }
    }

    @Test
    public void addSupplier() {
        Supplier newSupplier = new Supplier("Yossi", 2, "123", "check", false);
        suppliersFacade.addSupplier(newSupplier);
        Supplier addedSupplier = suppliersFacade.getSupplier(newSupplier.getId());
        assertEquals(newSupplier, addedSupplier);
    }

    @Test
    public void changeSupplierName() {
        suppliersFacade.changeSupplierName(supplier.getId(), "Yossi");
        assertEquals("Yossi", suppliersFacade.getSupplier(supplier.getId()).getName());
    }

    @Test
    public void changeSupplierBankAccount() {
        suppliersFacade.changeSupplierBankAccount(supplier.getId(), "456");
        assertEquals("456", suppliersFacade.getSupplier(supplier.getId()).getBankAccount());
    }

    @Test
    public void changeSupplierPaymentMethod() {
        suppliersFacade.changeSupplierPaymentMethod(supplier.getId(), "cash");
        assertEquals("cash", suppliersFacade.getSupplier(supplier.getId()).getPaymentMethod());
    }

    @Test
    public void changeSupplierIsDelivering() {
        suppliersFacade.changeSupplierIsDelivering(supplier.getId(), true);
        assertTrue(suppliersFacade.getSupplier(supplier.getId()).isDelivering());
    }

    @Test
    public void addSupplierContact() {
        suppliersFacade.getSupplier(supplier.getId()).addContact("avi", "ron");
        assertEquals("ron", suppliersFacade.getSupplier(supplier.getId()).getContactDetails("avi"));
    }

    @Test
    public void removeSupplierContact() {
        suppliersFacade.getSupplier(supplier.getId()).addContact("avi", "ron");
        suppliersFacade.getSupplier(supplier.getId()).removeContact("avi");
        assertNull(suppliersFacade.getSupplier(supplier.getId()).getContactDetails("avi"));
    }

}