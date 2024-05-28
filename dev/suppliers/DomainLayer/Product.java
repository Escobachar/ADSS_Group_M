package suppliers.DomainLayer;

public class Product {
    private String name;
    private int catalogNumber;
    private double price;
    private int supplierID;
    private Category category;
    private DiscountQuantity discount;
    private int ordersCount;

    public Product(String name, int catalogNumber, double price, int supplierID, Category category,
            DiscountQuantity discount) {
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
        this.supplierID = supplierID;
        this.category = category;
        this.discount = discount;
        this.ordersCount = 0;
    }

    public Product(String name, int catalogNumber, double price, int supplierID, Category category,
            DiscountQuantity discount, int ordersCount) {
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
        this.supplierID = supplierID;
        this.category = category;
        this.discount = discount;
        this.ordersCount = ordersCount;
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

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void incrementPrice(double increment) {
        this.price += increment;
    }

    public void decrementPrice(double decrement) {
        decrement = Math.abs(decrement);
        if (decrement >= price) {
            price = 0;
        } else {
            price -= decrement;
        }
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

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
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

    public void setDiscount(DiscountQuantity discount) {
        this.discount = discount;
    }

    public double calculatePrice(int amount) {
        if (discount.getAmount() <= amount)
            return (discount.getDiscountPrecentage() / 100) * amount;
        else
            return this.price * amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", catalogNumber=" + catalogNumber +
                ", price=" + price +
                ", supplierID=" + supplierID +
                ", category=" + category.toString() +
                ", discount=" + discount.toString() +
                ", quantityBought=" + ordersCount +
                '}';
    }
}