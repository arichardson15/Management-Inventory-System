package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product model
 */
public class Product {
    private int id;
    private String productName;
    private double productPrice;
    private int stock;
    private int max;
    private int min;;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * Constructor for Product
     * @param id
     * @param productName
     * @param productPrice
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String productName, double productPrice, int stock, int min, int max) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stock = stock;
        this.min = min;
        this.max = max;
         }

    /**
     *Getter and Setter for the different Product attributes.
     */

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }



    /**
     *
     * @param part add the associated part
     */
    public void addAssociatedPart (Part part){
        associatedParts.add(part);

    }

    /**
     *
     * @param selectedAssociatedPart deletes the associated parts from the selected product.
     * @return
     */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }





    /**
     *
     * @return gets list of all associated parts for the product.
     */

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }




}
