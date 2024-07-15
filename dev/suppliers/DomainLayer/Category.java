package suppliers.DomainLayer;

public class Category {
    private int categoryId;
    private String categoryName;

    public Category(String name, int number) {
        this.categoryId = number;
        this.categoryName = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

}