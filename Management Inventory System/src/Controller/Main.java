package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, String> partStockCol;


    @FXML
    private TableColumn<Product, Integer>productIdCol;

    @FXML
    private TableColumn<Product, String>productNameCol;

    @FXML
    private TableColumn<Product, String> productStockCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TextField productSearchBar;

    @FXML
    private Button partSearchBtn;

    @FXML
    private Button productSearchBtn;

    private static Part partToModify;
    private static Product productToModify;


    /**
     *searches the part table for ID.
     */
    boolean isNumber(){
        try{
            Integer.parseInt(partSearchBar.getText());
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    /**
     *@param id searches all parts for a matching id and sets table view to matching ids.
     */
    public void searchId(int id) {
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList();
        for(Part part : Inventory.getAllParts()) {
            if (part.getId() == id){
                searchedPartList.add(part);
            }
        }
        partTableView.setItems(searchedPartList);
    }
    /**
     *@param name makes the input and the part names lowercase and sets the items in table view to matching parts.
     */
    public void searchText(String name) {
        ObservableList<Part> searchedPartList =FXCollections.observableArrayList();
        for (Part part : Inventory.getAllParts()){
            if (part.getPartName().toLowerCase().contains(name.toLowerCase())){
                searchedPartList.add(part);
            }
        }partTableView.setItems(searchedPartList);
    }
    /**
     *@param event onActionSearchParts int call on search ID if string calls on search text.
     */
    @FXML
    void onActionSearchParts(ActionEvent event) throws IOException {

            if (isNumber()) {
                searchId(Integer.parseInt(partSearchBar.getText()));
            } else{
                searchText(partSearchBar.getText());
            }
    }
    /**
     *@param event onActionClearPartSearch clears the part search bar and brings all parts back to the screen.
     */
    @FXML
    void onActionClearPartSearch(ActionEvent event) throws IOException{
        partTableView.setItems(Inventory.getAllParts());
        partSearchBar.clear();
    }
    /**
     *@param event onActionClearProductSearch clears the product search bar and brings all products back to the screen.
     */
    @FXML
    void onActionClearProductSearch(ActionEvent event) throws IOException{
        productTableView.setItems(Inventory.getAllProducts());
        productSearchBar.clear();

    }

    boolean isId(){
        try{
            Integer.parseInt(productSearchBar.getText());
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    /**
     *@param id searches for a matching id among all products and the given id.
     */
    public void searchProductId(int id) {
        ObservableList<Product> searchedProductList = FXCollections.observableArrayList();
        for(Product product : Inventory.getAllProducts()) {
            if (product.getId() == id){
                searchedProductList.add(product);
            }
        }
        productTableView.setItems(searchedProductList);
    }
    /**
     *@param name searches for a matching string among all products and the given text.
     */
    public void searchName(String name) {
        ObservableList<Product> searchedProductList =FXCollections.observableArrayList();
        for (Product product : Inventory.getAllProducts()){
            if (product.getProductName().toLowerCase().contains(name.toLowerCase())){
                searchedProductList.add(product);
            }
        }productTableView.setItems(searchedProductList);
    }
    /**
     *@param event onActionSearchProducts if int calls on isId if string calls on searchName.
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) throws IOException{
        if (isId()) {
            searchProductId(Integer.parseInt(productSearchBar.getText()));
        } else{
            searchName(productSearchBar.getText());
        }
    }
    /**
     *@param event onActionAddPart loads the addPart screen.
     */

    @FXML
    void onActionAddPart (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
     *@param event onActionModifyPart loads the modifyPart screen while plugging the selected part into partToModify that is then pulled into the ModifyPart screen.
     */
    @FXML
    void onActionModifyPart (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        ModifyPart modifyPart = loader.getController();
        partToModify = partTableView.getSelectionModel().getSelectedItems().get(0);
        modifyPart.partPicked(partToModify);
        stage.show();

    }
    /**
     *@param event onActionAddProduct loads the addProduct screen.
     */
    @FXML
    void onActionAddProduct (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
     *@param event onActionModifyProduct loads the modifyProduct screen while plugging the selected product into productToModify that is then pulled into the ModifyProduct screen.
     */
    @FXML
    void onActionModifyProduct (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        ModifyProduct modifyProduct = loader.getController();
        productToModify = productTableView.getSelectionModel().getSelectedItems().get(0);
        modifyProduct.productPicked(productToModify);
        stage.show();

    }

    /**
     *@param event onActionDeletePart Deletes selected part.
     */
    @FXML
    void onActionDeletePart (ActionEvent event) throws IOException {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning Dialog");
        alert.setContentText("Are you sure you wish to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
            return;
        }
    }
    /**
     *@param event onActionDeleteProduct Deletes selected product and makes sure there are no associated parts.
     */
    @FXML
    void onActionDeleteProduct (ActionEvent event) throws IOException {

        ObservableList<Part> associatedPartsList = productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts();
        if (associatedPartsList.size() >=1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Cannot delete a product with associated parts.");
            alert.showAndWait();
            return;
        } else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Are you sure you wish to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
                return;
            }
        }
    }

    /**
     *@param event onActionExit Closes the application.
     */
    @FXML
    void onActionExit (ActionEvent event) {
        System.exit(0);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }
}




