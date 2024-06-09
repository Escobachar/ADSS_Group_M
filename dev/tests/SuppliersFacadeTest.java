package tests;

import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Supplier;
import suppliers.DomainLayer.SuppliersFacade;
import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.DiscountQuantity;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SuppliersFacadeTest {
    private static SuppliersFacade suppliersFacade;
    private static Supplier supplier;
    private static Product productMilk;
    private final static int supplierId = 1;
    private static Category categoryDairy;
    private static DiscountQuantity discountQuantity;
    private static boolean isSetUpComplete = false;
    private static Supplier newSupplier;

    @Before
    public void setUp() {
        if (isSetUpComplete) {
            return;
        }
        suppliersFacade = SuppliersFacade.getInstance();
        supplier = new Supplier("Shimon", supplierId, "123", "check", false, "here");
        suppliersFacade.addSupplier(supplier);
        categoryDairy = new Category("Dairy", 0);
        discountQuantity = DiscountQuantity.createDiscountQuantity(1, 10, 5.9);
        productMilk = new Product("milk", 1, 5.9, categoryDairy, discountQuantity);
        // Add the product to the supplier
        suppliersFacade.addProductToSupplier(supplier.getId(), productMilk);
        isSetUpComplete = true;
    }

    @Test
    public void A_testGetProductInSupplier() {
        Product product = suppliersFacade.getProductInSupplier(supplier.getId(), categoryDairy,
                productMilk.getCatalogNumber());
        assertNotNull("Product should not be null", product);
        assertEquals("milk", product.getName());
        assertEquals(5.9, product.getPrice(), 0.00);
        assertEquals(categoryDairy, product.getCategory());
        assertEquals(discountQuantity.getAmount(), product.getDiscount().getAmount());
    }

    @Test
    public void B_testEditProductInSupplier() {
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
    public void C_testRemoveProductFromSupplier() {
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
    public void D_removeSupplier() {
        suppliersFacade.removeSupplier(supplier.getId());
        try {
            suppliersFacade.getSupplier(supplier.getId());
            fail("Supplier should not be found");
        } catch (IllegalArgumentException e) {
            assertEquals("Supplier with ID " + supplier.getId() + " not found", e.getMessage());
        }
    }
    
    @Test
    public void E_addSupplier() {
        newSupplier = new Supplier("Yossi", 2, "123", "check", false, "there");
        suppliersFacade.addSupplier(newSupplier);
        Supplier addedSupplier = suppliersFacade.getSupplier(newSupplier.getId());
        assertEquals(newSupplier.getId(), addedSupplier.getId());
    }
    @Test(expected = IllegalArgumentException.class)
    public void F_Fail_addSupplier() {
        Supplier BadNewSupplier = new Supplier("not Yossi", 2, "123", "check", false, "there");
        suppliersFacade.addSupplier(BadNewSupplier);
    }
    @Test
    public void G_changeSupplierName() {
        suppliersFacade.changeSupplierName(newSupplier.getId(), "new Yossi");
        assertEquals("new Yossi", suppliersFacade.getSupplier(newSupplier.getId()).getName());
    }

    @Test
    public void H_changeSupplierBankAccount() {
        suppliersFacade.changeSupplierBankAccount(newSupplier.getId(), "456");
        assertEquals("456", suppliersFacade.getSupplier(newSupplier.getId()).getBankAccount());
    }

    @Test
    public void I_changeSupplierPaymentMethod() {
        suppliersFacade.changeSupplierPaymentMethod(newSupplier.getId(), "cash");
        assertEquals("cash", suppliersFacade.getSupplier(newSupplier.getId()).getPaymentMethod());
    }

    @Test
    public void J_changeSupplierIsDelivering() {
        suppliersFacade.changeSupplierIsDelivering(newSupplier.getId(), true);
        assertTrue(suppliersFacade.getSupplier(newSupplier.getId()).isDelivering());
    }

    @Test
    public void K_addSupplierContact() {
        suppliersFacade.getSupplier(newSupplier.getId()).addContact("avi", "ron");
        assertEquals("ron", suppliersFacade.getSupplier(newSupplier.getId()).getContactDetails("avi"));
    }

    @Test
    public void L_removeSupplierContact() {
        suppliersFacade.getSupplier(newSupplier.getId()).addContact("avi", "ron");
        suppliersFacade.getSupplier(newSupplier.getId()).removeContact("avi");
        assertNull(suppliersFacade.getSupplier(newSupplier.getId()).getContactDetails("avi"));
    }

}