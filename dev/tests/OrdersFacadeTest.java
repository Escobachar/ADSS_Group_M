package tests;

import org.junit.Before;
import org.junit.Test;
import suppliers.DomainLayer.OrdersFacade;
import suppliers.DomainLayer.Order;
import suppliers.DomainLayer.Product;
import suppliers.DomainLayer.Category;
import suppliers.DomainLayer.DiscountQuantity;
import suppliers.DomainLayer.Supplier;
import static suppliers.DaysOfTheWeek.Day;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;

public class OrdersFacadeTest {
    private OrdersFacade ordersFacade;
    private Order order;
    private Product productMilk;
    private Category categoryDairy;
    private DiscountQuantity discountQuantity;
    private Supplier supplier;

    @Before
    public void setUp() {
        ordersFacade = OrdersFacade.getInstance();
        supplier = new Supplier("Shimon", 1, "123", "check", false, "here");
        categoryDairy = new Category("Dairy", 0);
        discountQuantity = new DiscountQuantity(1, 10, 5.9);
        productMilk = new Product("milk", 1, 5.9, categoryDairy, discountQuantity);
        HashMap<Product,Integer> items = new HashMap<Product,Integer>();
        items.put(productMilk, 5);
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        ordersFacade.addOrder(supplier, new Date(), new Date(), items, days);
    }
    //fail test
    @Test(expected = IllegalArgumentException.class)
    public void testAddOrderByParamsEmptyItems() {
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        ordersFacade.addOrder(supplier, new Date(), new Date(), null, days);
        assertTrue(ordersFacade.getOrder(1) != null);
    }
    //fail test
    @Test(expected = IllegalArgumentException.class)
    public void testAddOrderByParamsEmptyDays() {
        HashMap<Product,Integer> items = new HashMap<Product,Integer>();
        items.put(productMilk, 5);
        ordersFacade.addOrder(supplier, new Date(), new Date(), items, null);
        assertTrue(ordersFacade.getOrder(1) != null);
    }
    @Test
    public void testAddOrderByParams() {
        HashMap<Product,Integer> items = new HashMap<Product,Integer>();
        items.put(productMilk, 5);
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        ordersFacade.addOrder(supplier, new Date(), new Date(), items, days);
        assertTrue(ordersFacade.getOrder(1) != null);
    }
    @Test
    // check if really needed
    public void testAddOrderByOrder() {
        HashMap<Product,Integer> items = new HashMap<Product,Integer>();
        items.put(productMilk, 5);
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        order = new Order(2, supplier, new Date(), new Date(), items, days);
        ordersFacade.addOrder(order);
        assertTrue(ordersFacade.getOrder(2) != null);
    }

    @Test
    public void testAddOrderConstDeliveryDay() {   
        ordersFacade.addOrderConstDeliveryDay(1, Day.SUNDAY);
        assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().contains(Day.SUNDAY));
    }
    @Test
    public void testRemoveConstDeliveryDay() {
        ordersFacade.removeOrderConstDeliveryDay(1, Day.SUNDAY); // need to add day
        assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().isEmpty());
    }
    public void testAddOrderConstDeliveryDays() {
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        days.add(Day.MONDAY);
        ordersFacade.addOrderConstDeliveryDays(1, days);
        assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().contains(Day.SUNDAY));
        assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().contains(Day.MONDAY));
    }
    public void testRemoveOrderConstDeliveryDays() {
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        days.add(Day.MONDAY);
        ordersFacade.addOrderConstDeliveryDays(1, days);
        ordersFacade.removeOrderConstDeliveryDays(1, days);
        assertTrue(ordersFacade.getOrder(1).getConstDeliveryDays().isEmpty());
    }
    // @Test
    // public void testChangeOrderItemQuantity() {
    //     ordersFacade.ChangeOrderItemQuantity(1, productMilk.getCatalogNumber(), 5);
    //     assertTrue(ordersFacade.getOrder(1).getItems().get(productMilk) == 5);
    //     ordersFacade.ChangeOrderItemQuantity(1, productMilk.getCatalogNumber(), 0);
    //     assertTrue(ordersFacade.getOrder(1).getItems().get(productMilk) == null);
    // }
    //fail test
    @Test(expected = IllegalArgumentException.class)
    public void TestRemoveOrder() {
        ordersFacade.removeOrder(1);
        assertTrue(ordersFacade.getOrder(1) == null);
    }
    @Test 
    public void TestsetOrderDeliveryDate(){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date deliveryDate = java.sql.Date.valueOf(tomorrow);
        ordersFacade.setOrderDeliveryDate(1, deliveryDate);
        assertTrue(ordersFacade.getOrder(1).getDeliveryDate().equals(deliveryDate));
    }
    //fail test
    @Test(expected = IllegalArgumentException.class)
    public void TestsetOrderDeliveryDateBeforeCreationDate(){
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        Date deliveryDate = java.sql.Date.valueOf(yesterdayDate);
        ordersFacade.setOrderDeliveryDate(1,deliveryDate);
    }
    @Test
    public void testGetThisWeekOrders(){
        List<Day> days = new ArrayList<Day>();
        HashMap<Product,Integer> items = new HashMap<Product,Integer>();
        items.put(productMilk, 5);
        LocalDate not_include = LocalDate.now().plusDays(8);
        Date deliveryDate = java.sql.Date.valueOf(not_include);
        ordersFacade.addOrder(supplier, new Date(), deliveryDate, items, days);
        HashMap<Integer,Order> orders = ordersFacade.getThisWeekOrders();
        assertTrue(orders.size() == 1);
    }
    @Test
    public void testgetThisWeekPickupOrders(){
        List<Day> days = new ArrayList<Day>();
        days.add(Day.SUNDAY);
        HashMap<Product,Integer> items = new HashMap<Product,Integer>();
        items.put(productMilk, 5);
        LocalDate not_include = LocalDate.now().plusDays(10000000);
        Date deliveryDate = java.sql.Date.valueOf(not_include);
        ordersFacade.addOrder(supplier, new Date(), deliveryDate, items, days);
        HashMap<Integer,Order> orders = ordersFacade.getThisWeekPickupOrders();
        int ordersSize = orders.size();
        assertTrue(orders.size() == 2);
    }
}
