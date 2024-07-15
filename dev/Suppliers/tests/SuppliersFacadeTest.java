//  package Suppliers.tests;

//  import org.junit.Before;
//  import org.junit.FixMethodOrder;
//  import org.junit.Test;
//  import static org.junit.Assert.*;
//  import org.junit.runners.MethodSorters;

// import Suppliers.DataAccessLayer.DataBase;
// import Suppliers.DomainLayer.Category;
// import Suppliers.DomainLayer.DiscountQuantity;
// import Suppliers.DomainLayer.Product;
// import Suppliers.DomainLayer.Supplier;
// import Suppliers.DomainLayer.SuppliersFacade;

// import java.sql.SQLException;


//  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
//  public class SuppliersFacadeTest {
//      private static SuppliersFacade suppliersFacade;
//      private static Supplier supplier;
//      private static Product productMilk;
//      private final static int supplierId = 1;
//      private static Category categoryDairy;
//      private static DiscountQuantity discountQuantity;
//      private static boolean isSetUpComplete = false;
//      private static Supplier newSupplier;
//      private DataBase dataBase;

//      @Before
//      public void setUp() {
//          if (isSetUpComplete) {
//              return;
//          }
//          try {
//              dataBase.deleteAll();
//              suppliersFacade = SuppliersFacade.getInstance();
//              supplier = new Supplier("Shimon", supplierId, "123", "check", false, "here");
//              suppliersFacade.addSupplier(supplier);
//              categoryDairy = new Category("Dairy", 0);
//              discountQuantity = DiscountQuantity.createDiscountQuantity(1, 10, 5.9);
//              productMilk = new Product("milk", 1, 5.9, categoryDairy, discountQuantity);
//              // Add the product to the supplier
//              suppliersFacade.addProductToSupplier(supplier.getId(), productMilk);
//              isSetUpComplete = true;
//          }
//          catch (Exception e)
//          {
//              isSetUpComplete = false;
//          }
//      }

//      @Test
//      public void A_testGetProductInSupplier() {
//          Product product = suppliersFacade.getProductInSupplier(supplier.getId(), categoryDairy,
//                  productMilk.getCatalogNumber());
//          assertNotNull("Product should not be null", product);
//          assertEquals("milk", product.getName());
//          assertEquals(5.9, product.getPrice(), 0.00);
//          assertEquals(categoryDairy, product.getCategory());
//          assertEquals(discountQuantity.getAmount(), product.getDiscount().getAmount());
//      }

//      @Test
//      public void B_testEditProductInSupplier() {
//          try {
//              suppliersFacade.setPrice(6.9, supplier.getId(), productMilk.getCatalogNumber());
//              suppliersFacade.setDiscountAmount(3, supplier.getId(), productMilk.getCatalogNumber());
//              suppliersFacade.setDiscountPercentage(0.2, supplier.getId(), productMilk.getCatalogNumber());
//              suppliersFacade.setProductName("soy milk", supplier.getId(), productMilk.getCatalogNumber());
//          }
//          catch (Exception e){
//                 fail("error in testEditProductInSupplier: " + e.getMessage());
//          }

//          Product editedProduct = suppliersFacade.getProductInSupplier(supplier.getId(), categoryDairy,
//                  productMilk.getCatalogNumber());

//          assertEquals("soy milk", editedProduct.getName());
//          assertEquals(6.9, editedProduct.getPrice(), 0.00); // Use delta value to account for floating-point precision
//          assertEquals(1, editedProduct.getCatalogNumber());
//          assertEquals(3, editedProduct.getDiscount().getAmount());
//          assertEquals(0.2, editedProduct.getDiscount().getDiscountPercentage(), 0.00);
//      }

//      @Test
//      public void C_testRemoveProductFromSupplier() {
//          try {
//              suppliersFacade.removeProductFromSupplier(supplier.getId(), productMilk.getCatalogNumber());
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          try {
//              suppliersFacade.getProductInSupplier(supplier.getId(), categoryDairy, productMilk.getCatalogNumber());
//              fail("Product should not be found");
//          } catch (IllegalArgumentException e) {
//              assertEquals("Product " + productMilk.getCatalogNumber() + " not found in supplier " + supplier.getId(),
//                      e.getMessage());
//          }
//      }

//      @Test
//      public void D_removeSupplier() {
//          try {
//              suppliersFacade.removeSupplier(supplier.getId());
//              suppliersFacade.getSupplier(supplier.getId());
//              fail("Supplier should not be found");
//          } catch (Exception e) {
//              assertEquals("Supplier with ID " + supplier.getId() + " not found", e.getMessage());
//          }
//      }

//      @Test
//      public void E_addSupplier() {
//          try {
//              newSupplier = new Supplier("Yossi", 2, "123", "check", false, "there");
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          suppliersFacade.addSupplier(newSupplier);
//          Supplier addedSupplier = suppliersFacade.getSupplier(newSupplier.getId());
//          assertEquals(newSupplier.getId(), addedSupplier.getId());
//      }
//      @Test(expected = IllegalArgumentException.class)
//      public void F_Fail_addSupplier() {
//          Supplier BadNewSupplier = null;
//          try {
//              BadNewSupplier = new Supplier("not Yossi", 2, "123", "check", false, "there");
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          suppliersFacade.addSupplier(BadNewSupplier);
//      }
//      @Test
//      public void G_changeSupplierName() {
//          try {
//              suppliersFacade.setSupplierName(newSupplier.getId(), "new Yossi");
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          assertEquals("new Yossi", suppliersFacade.getSupplier(newSupplier.getId()).getName());
//      }

//      @Test
//      public void H_changeSupplierBankAccount() {
//          try {
//              suppliersFacade.setSupplierBankAccount(newSupplier.getId(), "456");
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          assertEquals("456", suppliersFacade.getSupplier(newSupplier.getId()).getBankAccount());
//      }

//      @Test
//      public void I_changeSupplierPaymentMethod() {
//          try {
//              suppliersFacade.setSupplierPaymentOption(newSupplier.getId(), "cash");
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          assertEquals("cash", suppliersFacade.getSupplier(newSupplier.getId()).getPaymentMethod());
//      }

//      @Test
//      public void J_changeSupplierIsDelivering() {
//          suppliersFacade.changeSupplierIsDelivering(newSupplier.getId(), true);
//          assertTrue(suppliersFacade.getSupplier(newSupplier.getId()).isDelivering());
//      }

//      @Test
//      public void K_addSupplierContact() {
//          try {
//              suppliersFacade.getSupplier(newSupplier.getId()).addContact("avi", "ron");
//          } catch (SQLException e) {
//              throw new RuntimeException(e);
//          }
//          assertEquals("ron", suppliersFacade.getSupplier(newSupplier.getId()).getContactDetails("avi"));
//      }

//      @Test
//      public void L_removeSupplierContact() {
//          try {
//              suppliersFacade.getSupplier(newSupplier.getId()).addContact("ron", "avi");
//              suppliersFacade.getSupplier(newSupplier.getId()).removeContact("ron");
//          }
//          catch (Exception e){
//              System.out.println(e.getMessage());
//          }

//         assertNull(suppliersFacade.getSupplier(newSupplier.getId()).getContactDetails("ron"));
//      }
//      @Test
//      public void M_checkCheapestProductInSuppliers(){
//          try {
//              Supplier supplier1 = new Supplier("Shimon1", 10, "123", "check", false, "here");
//              Supplier supplier2 = new Supplier("Shimon2", 11, "123", "check", false, "here");
//              suppliersFacade.addSupplier(supplier1);
//              suppliersFacade.addSupplier(supplier2);

//              Product prod1 = new Product("tea", 1, 10, categoryDairy, discountQuantity);
//              Product prod2 = new Product("tea", 1, 5, categoryDairy, discountQuantity);
//              supplier1.addProduct(categoryDairy,prod1);
//              supplier2.addProduct(categoryDairy,prod2);
//              Supplier res = suppliersFacade.getCheapestSupplier("tea",10);
//              assertEquals(res.getId(), supplier2.getId());
//          }
//          catch (Exception e){
//              fail(e.getMessage());
//          }
//      }

//  }