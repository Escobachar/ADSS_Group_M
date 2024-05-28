package suppliers.DomainLayer;

public class DiscountQuantity {
    private int catalogNumber;
    private int amount;
    private double discountPrecentage;

    public DiscountQuantity(int catalogNumber, int amount, double singlePrice, double discountPrecentage) {
        this.catalogNumber = catalogNumber;
        this.amount = amount;
        this.discountPrecentage = discountPrecentage;
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

    public double getDiscountPrecentage() {
        return discountPrecentage;
    }

    public void setDiscountPrecentage(int discountPrecentage) {
        this.discountPrecentage = discountPrecentage;
    }

    public void incrementDiscountPrecentage(double increment) {
        if (discountPrecentage + increment > 100) {
            discountPrecentage = 100;
        } else {
            discountPrecentage += increment;
        }
    }

    public void decrementDiscountPrice(double decrement) {
        decrement = Math.abs(decrement);
        if (decrement >= discountPrecentage) {
            discountPrecentage = 0;
        } else {
            discountPrecentage -= decrement;
        }
    }

}
