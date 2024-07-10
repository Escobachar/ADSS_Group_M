// package tests;

// import org.junit.Test;
// import org.junit.Before;
// import suppliers.DataAccessLayer.DataBase;
// import suppliers.DomainLayer.OrdersFacade;
// import suppliers.DomainLayer.Order;
// import suppliers.DomainLayer.Product;
// import suppliers.DomainLayer.Category;
// import suppliers.DomainLayer.DiscountQuantity;
// import suppliers.DomainLayer.Supplier;

// import static org.junit.Assert.*;
// import static suppliers.DaysOfTheWeek.Day;
// import java.sql.SQLException;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import org.junit.runners.MethodSorters;
// import org.junit.FixMethodOrder;

// import java.util.HashMap;

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
// public class OrdersFacadeTest {
//     private static OrdersFacade ordersFacade;
//     private static Order order;
//     private static Product productMilk;
//     private static Category categoryDairy;
//     private static DiscountQuantity discountQuantity;
//     private static Supplier supplier;
//     private static boolean isSetUpComplete = false;
//     private DataBase dataBase;

//     @Before
//     public void setUp() {
//         try{
//             if (isSetUpComplete) {
//                 return;
//             }
//             dataBase.deleteAll();
//             ordersFacade = OrdersFacade.getInstance();
//             supplier = new Supplier("Shimon", 1, "123", "check", false, "here");
//             categoryDairy = new Category("Dairy", 0);
//             discountQuantity = DiscountQuantity.createDiscountQuantity(1, 10, 5.9);
//             productMilk = new Product("milk", 1, 5.9, categoryDairy, discountQuantity);
//             HashMap<Product,Integer> items = new HashMap<Product,Integer>();
//             items.put(productMilk, 5);
//             List<Day> days = new ArrayList<Day>();
//             days.add(Day.SUNDAY);
//             ordersFacade.addOrder(supplier, new Date(), new Date(), items, days);
//             isSetUpComplete = true;
//         }
//         catch (Exception e){
//         }
//     }
//     //fail test
//     @Test(expected = IllegalArgumentException.class)
//     public void A_testAddOrderByParamsEmptyItems() {
//         try{
//         List<Day> days = new ArrayList<Day>();
//         days.add(Day.SUNDAY);
//         ordersFacade.addOrder(supplier, new Date(), new Date(), null, days);
//         assertTrue(ordersFacade.getOrder(1) != null);
//         }
//         catch (SQLException e){
//         }
//     }
//     //fail test
//     @Test(expected = IllegalArgumentException.class)
//     public void B_testAddOrderByParamsEmptyDays() {
//         try{
//         HashMap<Product,Integer> items = new HashMap<Product,Integer>();
//         items.put(productMilk, 5);
//         ordersFacade.addOrder(supplier, new Date(), new Date(), items, null);
//         assertTrue(ordersFacade.getOrder(1) != null);
//         }
//         catch (SQLException e){
//         }
//     }
//     @Test
//     public void C_testAddOrderByParams() {
//         try{
//         HashMap<Product,Integer> items = new HashMap<Product,Integer>();
//         items.put(productMilk, 5);
//         List<Day> days = new ArrayList<Day>();
//         days.add(Day.SUNDAY);
//         ordersFacade.addOrder(supplier, new Date(), new Date(), items, days);
//         assertTrue(ordersFacade.getOrder(1) != null);
//         }
//         catch (SQLException e){
//             System.out.println(e.getMessage());
//         }
//     }
//     @Test
//     // check if really needed
//     public void D_testAddOrderByOrder() {
//         try{
//         HashMap<Product,Integer> items = new HashMap<Product,Integer>();
//         items.put(productMilk, 5);
//         List<Day> days = new ArrayList<Day>();
//         days.add(Day.SUNDAY);
//         ordersFacade.addOrder(supplier, new Date(), new Date(), items, days);
//         assertTrue(ordersFacade.getOrder(2) != null);
//         }
//         catch (SQLException e){
//         }
//     }

//     @Test
//     public void E_testAddOrderConstDeliveryDay() {   
//         try{
//         ordersFacade.addOrderConstDeliveryDay(1, Day.SUNDAY);
//         assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().contains(Day.SUNDAY));
//         }
//         catch (SQLException e){
//         }
//     }
//     @Test
//     public void F_testRemoveConstDeliveryDay() {
//         try{
//         ordersFacade.removeOrderConstDeliveryDay(1, Day.SUNDAY); // need to add day
//         assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().isEmpty());
//         }
//         catch (SQLException e){
//         }
//     }
//     public void G_testAddOrderConstDeliveryDays() {
//         try{
//         List<Day> days = new ArrayList<Day>();
//         days.add(Day.SUNDAY);
//         days.add(Day.MONDAY);
//         ordersFacade.addOrderConstDeliveryDays(1, days);
//         assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().contains(Day.SUNDAY));
//         assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().contains(Day.MONDAY));
//         }
//         catch (SQLException e){
//         }
//     }
//     public void H_testRemoveOrderConstDeliveryDays() {
//         try{
//         List<Day> days = new ArrayList<Day>();
//         days.add(Day.SUNDAY);
//         days.add(Day.MONDAY);
//         ordersFacade.addOrderConstDeliveryDays(1, days);
//         ordersFacade.removeOrderConstDeliveryDays(1, days);
//         assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().isEmpty());
//         }
//         catch (SQLException e){
//         }
//     }
//     @Test(expected = IllegalArgumentException.class)
//     public void I_TestRemoveOrder() {
//         try{
//         ordersFacade.removeOrder(1);
//         assertTrue(ordersFacade.getOrder(1) == null);
//         }
//         catch (SQLException e){
//             System.out.println(e.getMessage());
//         }
//     }
//     @Test 
//     public void J_TestsetOrderDeliveryDate(){
//         LocalDate tomorrow = LocalDate.now().plusDays(1);
//         Date deliveryDate = java.sql.Date.valueOf(tomorrow);
//         try {
//             ordersFacade.setOrderDeliveryDate(2, deliveryDate);
//         } catch (SQLException e) {
//             throw new RuntimeException(e);
//         }
//         assertTrue(ordersFacade.getOrder(2).getDeliveryDate().equals(deliveryDate));
//     }
//     //fail test
//     @Test(expected = IllegalArgumentException.class)
//     public void K_TestsetOrderDeliveryDateBeforeCreationDate(){
//         LocalDate yesterdayDate = LocalDate.now().minusDays(1);
//         Date deliveryDate = java.sql.Date.valueOf(yesterdayDate);
//         try {
//             ordersFacade.setOrderDeliveryDate(1,deliveryDate);
//         } catch (SQLException e) {
//             throw new RuntimeException(e);
//         }
//     }
//     @Test
//     public void L_testGetThisWeekOrders(){
//         try{
//         List<Day> days = new ArrayList<Day>();
//         HashMap<Product,Integer> items = new HashMap<Product,Integer>();
//         items.put(productMilk, 5);
//         LocalDate not_include = LocalDate.now().plusDays(8);
//         Date deliveryDate = java.sql.Date.valueOf(not_include);
//         ordersFacade.addOrder(supplier, new Date(), deliveryDate, items, days);
//         HashMap<Integer,Order> orders = ordersFacade.getThisWeekOrders();
//         assertTrue(orders.size() == 2);
//         }
//         catch (SQLException e){
//         }
//     }
//     @Test
//     public void M_testgetThisWeekPickupOrders() {
//         try {
//             List<Day> days = new ArrayList<Day>();
//             days.add(Day.SUNDAY);
//             HashMap<Product, Integer> items = new HashMap<Product, Integer>();
//             items.put(productMilk, 5);
//             LocalDate not_include = LocalDate.now().plusDays(10000000);
//             Date deliveryDate = java.sql.Date.valueOf(not_include);
//             Supplier newSupplier = new Supplier("new Shimon", 1, "123", "check", true, "here");
//             ordersFacade.addOrder(newSupplier, new Date(), deliveryDate, items, days);
//             HashMap<Integer, Order> orders = ordersFacade.getThisWeekPickupOrders();
//             assertTrue(orders.size() == 2);
//         } catch (SQLException e) {
//         }
//     }
//     @Test
//     public void N_testOrderPrice(){
//         assertTrue(ordersFacade.getOrderPrice(2) == 5.9*5);
//     }
//     @Test
//     public void O_testcannotEditOrderOneDayBeforeDelivery(){
//         HashMap<Product,Integer> items = new HashMap<Product,Integer>();
//         items.put(productMilk, 5);
//         List<Day> days = new ArrayList<Day>();
//         days.add(Day.SUNDAY);
//         int id = 1;
//         try {
//         id = ordersFacade.addOrder(supplier, new Date(), new Date(), items, days);
//         }
//         catch (Exception e){
//             fail(e.getMessage());
//         }
//         Order ord = ordersFacade.getOrder(id);
//         boolean canChange = ordersFacade.isOrderCanBeEdit(id);
//         assertFalse(canChange);
//     }
// }