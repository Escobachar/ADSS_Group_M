package suppliers;
import java.util.Date;
import java.util.HashMap;

public class Order {
    private int supplierId;
    final private Date creationDate;
    private Date deliveryDate;
    private HashMap<Product, Integer> items;
    private double price;
    private boolean isChanged;
    private boolean isDelivering;

    // Constructor
    public Order(int supplierId, Date creationDate, Date deliveryDate, HashMap<Product, Integer> items, boolean isDelivering) {
        this.supplierId = supplierId;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.items = items;
        priceCalculation();
        this.isChanged = false;
        this.isDelivering = isDelivering;
    }
    // Getters and Setters
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public HashMap<Product, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Product, Integer> items) {
        this.items = items;
        isChanged = true;
    }

    public void removeItem(Product product){
        if (this.items.remove(product) != null)
        {
            isChanged = true;
        }
    }
    public void addItem(Product product, Integer amount){
        if (this.items.get(product) != amount)
        {
            this.items.put(product, amount);
            isChanged = true;
        }
    }

    public double getPrice() {
        if(isChanged)
            priceCalculation();
        return price;
    }
    //todo
    private void priceCalculation(){
        double newPrice = 0;

        for (HashMap.Entry<Product, Integer> entry : this.items.entrySet())
        {
            Product product = entry.getKey();
            DiscountQuantity discountQuantity = product.getDiscount();
            if(discountQuantity.getAmount() <= entry.getValue())
                newPrice += discountQuantity.getDiscountPrice() * entry.getValue();
            else
                newPrice += product.getPrice()*entry.getValue();
        }
        price = newPrice;
        isChanged = false;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public boolean isDelivering() {
        return isDelivering;
    }

    public void setDelivering(boolean delivering) {
        isDelivering = delivering;
    }




    @Override
    public String toString() {
        return "Order{" +
                "supplierId=" + supplierId +
                ", creationDate=" + creationDate +
                ", deliveryDate=" + deliveryDate +
                ", items=" + items +
                ", price=" + getPrice() +
                ", isDelivering=" + isDelivering +
                '}';
    }
}