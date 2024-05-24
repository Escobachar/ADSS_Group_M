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

    public void incrementAmount(int increment) {
        amount += increment;
    }

    public void decrementAmount(int decrement) {
        decrement = Math.abs(decrement);
        if (decrement >= amount) {
            amount = 0;
        } else {
            amount -= decrement;
        }
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public void incrementSinglePrice(double increment) {
        singlePrice += increment;
    }

    public void decrementSinglePrice(double decrement) {
        decrement = Math.abs(decrement);
        if (decrement >= singlePrice) {
            singlePrice = 0;
        } else {
            singlePrice -= decrement;
        }
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void incrementDiscountPrice(double increment) {
        discountPrice += increment;
    }

    public void decrementDiscountPrice(double decrement) {
        decrement = Math.abs(decrement);
        if (decrement >= discountPrice) {
            discountPrice = 0;
        } else {
            discountPrice -= decrement;
        }
    }

    public double calculateDiscount(int boughtAmount) {
        return boughtAmount >= amount ? discountPrice * boughtAmount : 0;
    }

    public double totalPrice(int boughtAmount) {
        return boughtAmount * singlePrice - calculateDiscount(boughtAmount);
    }
}
