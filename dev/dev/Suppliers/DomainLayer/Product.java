package Suppliers.DomainLayer;

import Suppliers.DataAccessLayer.DAO.ProductsDiscountDAO;
import java.sql.SQLException;

public class Product {
    private String name;
    private int catalogNumber;
    private double price;
    private Category category;
    private DiscountQuantity discount;
    private ProductsDiscountDAO productsDiscountDAO;
    private int ordersCount;

    public Product(String name, int catalogNumber, double price, Category category,
            DiscountQuantity discount) throws SQLException {
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
        this.category = category;
        this.discount = discount;
        this.ordersCount = 0;
        productsDiscountDAO = new ProductsDiscountDAO();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public void incrementOrdersCount() {
        ordersCount++;
    }

    public void decrementOrdersCount() {
        ordersCount--;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public DiscountQuantity getDiscount() {
        return discount;
    }

    public double calculatePrice(int amount) {
        if (discount.getAmount() <= amount)
            return this.price * (1 - discount.getDiscountPercentage() / 100) * amount;
        else
            return this.price * amount;
    }

    public String productToString(int amount) {

        String prodact = "";
        prodact += "Catalog Number: "+String.valueOf(this.catalogNumber) + "| ";
        prodact += "Name: "+this.name + "| ";
        prodact += "Amount: "+String.valueOf(amount) + "| ";
        prodact += "Single Price: "+String.valueOf(this.price) + "| ";
        double discountForAmount = (this.discount.getAmount() <= amount) ? getDiscount().getDiscountPercentage() : 1;
        prodact += "Discount: "+String.valueOf(discountForAmount) + "| ";
        prodact += "Total Price: "+String.valueOf(calculatePrice(amount));
        return prodact;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", catalogNumber=" + catalogNumber +
                ", price=" + price +
                ", category=" + category.toString() +
                ", discount=" + discount.toString() +
                ", quantityBought=" + ordersCount +
                '}';
    }

    public void setDiscountAmount(int supplierId, int newDiscountAmount) throws SQLException {
        discount.setAmount(newDiscountAmount);
        productsDiscountDAO.updateDiscountQuantity(supplierId,catalogNumber,discount);
    }

    public void setDiscountPercentage(int supplierId, double newDiscount) throws SQLException {
        discount.setDiscount(newDiscount);
        productsDiscountDAO.updateDiscountQuantity(supplierId,catalogNumber,discount);
    }
}