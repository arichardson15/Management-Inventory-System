package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    /**
     * @param part and product ID are automatically set. Set's starting ids at 0.
     */
    private static int partId = 0;

    private static int productId = 0;

    /**
     * @param Array list for all Parts and all products.
     */

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();


    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *@param Array list for associated parts
     */

    private static ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param part Adds a part, updates a part and sets its index.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }
    /**
     * @param product adds a product, updates a product and sets its index.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static void updateProduct(int index, Product product){
        allProducts.set(index, product);
    }

    /**
     *
     * @return assigns Part Id, line 47 assigns a Product Id.
     */
    public static int assignPartId(){
        partId++;
        return partId;
    }

    public static int assignProductId(){
        productId++;
        return productId;
    }

    /**
     * Gets a list of all Parts, line 53 gets a list of all products.
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static ObservableList<Part> getAssociatedParts() {
        return allAssociatedParts;
    }

    /**
     *
     * @param selectedPart deletes selected part. line 78 deletes selected product.
     * @return
     */
    public static boolean deletePart(Part selectedPart) {

        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {

        return allProducts.remove(selectedProduct);

    }
}

