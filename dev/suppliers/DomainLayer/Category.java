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

    public void setCategoryId(int number) {
        this.categoryId = number;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }
}
