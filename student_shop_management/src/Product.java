public class Product {
    private String itemName;
    private int quantity;
    private double price;
    private String addDate;

    // Constructor to initialize all fields
    public Product(String itemName, int quantity, double price, String addDate) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.addDate = addDate;
    }

    // Getter method for itemName
    public String getItemName() {
        return itemName;
    }

    // Getter method for quantity
    public int getQuantity() {
        return quantity;
    }

    // Getter method for price
    public double getPrice() {
        return price;
    }

    // Getter method for addDate
    public String getAddDate() {
        return addDate;
    }

    public Object getName() {
        return itemName;
    }
}
