package suppliers;

public class DiscountQuantity {
    private int catalogNumber;
    private int amount;
    private double singlePrice;
    private double discountPrice;

    public DiscountQuantity(int catalogNumber, int amount, double singlePrice, double discountPrice) {
        this.catalogNumber = catalogNumber;
        this.amount = amount;
        this.singlePrice = singlePrice;
        this.discountPrice = discountPrice;
    }

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

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double calculateDiscount(int boughtAmount) {
        return boughtAmount >= amount ? discountPrice * boughtAmount : 0;
    }

    public double totalPrice(int boughtAmount) {
        return boughtAmount * singlePrice - calculateDiscount(boughtAmount);
    }
}
