import java.util.ArrayList;

public class transaction {
    private static ArrayList<Product> productList;
    private static double totalPrice;

    public transaction() {
        this.productList = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addProduct(Product product) {
        productList.add(product);
        totalPrice += product.getQuantity() * product.getPrice();
    }

    public static double getTotalPrice() {
        return totalPrice;
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static void clearTransaction() {
        productList.clear();
        totalPrice = 0.0;
    }
}
