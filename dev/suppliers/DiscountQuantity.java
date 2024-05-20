package suppliers;

public class DiscountQuantity {
    private int catalogNumber;
    private int amount;
    private double discountPrice;

    // Constructor
    public DiscountQuantity(int catalogNumber, int amount, double discountPrice) {
        this.catalogNumber = catalogNumber;
        this.amount = amount;
        this.discountPrice = discountPrice;
    }

    // Getters and Setters
    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double CalculateDiscount(int boughtAmount) {
        return boughtAmount >= amount ? discountPrice * boughtAmount : 0;
    }

    public double TotalPrice(int boughtAmount) {
        return boughtAmount - CalculateDiscount(boughtAmount);
    }
}
