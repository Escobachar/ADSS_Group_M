package suppliers;

public class Category {
    private int number;
    private String name;

    // Constructor
    public Category(int number, String name) {
        this.number = number;
        this.name = name;
    }

    // Getters and Setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
