import java.util.ArrayList;
import java.util.List;


public class ProductManager {


    private static List<Product> productList = new ArrayList<>();




    public static List<Product> getProductList() {
        return productList;
    }




    public static void addProduct(Product product) {
        productList.add(product);
    }




    public static void updateProduct(int index, Product product) {
        productList.set(index, product);
    }




    public static void removeProduct(int index) {
        productList.remove(index);
    }




    public static Product getProductByName(String name) {
        for (Product product : productList) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
}
