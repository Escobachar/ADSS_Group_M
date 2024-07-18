package Suppliers.DomainLayer;

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


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDiscount(double discount) {
        this.discountPrecentage = discount;
    }


    public double getDiscountPercentage() {
        return discountPrecentage;
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