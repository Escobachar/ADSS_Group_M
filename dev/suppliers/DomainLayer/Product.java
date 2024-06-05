package suppliers.DomainLayer;

public class Product {
    private String name;
    private int catalogNumber;
    private double price;
    private Category category;
    private DiscountQuantity discount;
    private int ordersCount;

    public Product(String name, int catalogNumber, double price, Category category,
            DiscountQuantity discount) {
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
        this.category = category;
        this.discount = discount;
        this.ordersCount = 0;
    }

    public Product(String name, int catalogNumber, double price, int supplierID, Category category,
            DiscountQuantity discount, int ordersCount) {
        this.name = name;
        this.catalogNumber = catalogNumber;
        this.price = price;
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

    public String productToString(int amount) {

        String prodact= "";
        prodact += String.valueOf(this.catalogNumber) + " ";
        prodact+= this.name + " ";
        prodact+= String.valueOf(amount) + " ";
        prodact+= String.valueOf(this.price) + " ";
        double discountForAmount = (this.discount.getAmount() <= amount) ? getDiscount().getDiscountPrecentage() : 1;
        prodact+= String.valueOf(discountForAmount) + " ";
        prodact+= String.valueOf(discountForAmount * this.price * amount);
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
}