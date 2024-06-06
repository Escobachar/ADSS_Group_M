package suppliers.DomainLayer;

public class DiscountQuantity {
    private int catalogNumber;
    private int amount;
    private double discountPrecentage;

    public static DiscountQuantity createDiscountQuantity(int catalogNumber, int amount, double discountPrecentage) {
        if (amount < 0 || discountPrecentage < 0 || discountPrecentage > 100) {
            throw new IllegalArgumentException("Invalid amount or discount precentage");
        }
        return new DiscountQuantity(catalogNumber, amount, discountPrecentage);

    }

    private DiscountQuantity(int catalogNumber, int amount, double discountPrecentage) {
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

    public void setDiscount(double discount) {
        this.discountPrecentage = discount;
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

    @Override
    public String toString() {
        return "DiscountQuantity{" +
                "catalogNumber=" + catalogNumber +
                ", amount=" + amount +
                ", discountPrecentage=" + discountPrecentage +
                '}';
    }

}
