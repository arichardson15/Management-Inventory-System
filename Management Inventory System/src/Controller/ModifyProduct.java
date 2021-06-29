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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *declares global variables
 */
public class ModifyProduct implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    public TextField idTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partsIdCol;

    @FXML
    private TableColumn<Part, String> partsNameCol;

    @FXML
    private TableColumn<Part, Integer> partsInventoryCol;

    @FXML
    private TableColumn<Part, Integer> partsPriceCol;

    @FXML
    private TableColumn<Part, Integer> assoPartsIdCol;

    @FXML
    private TableColumn<Part, String> assoPartsNameCol;

    @FXML
    private TableColumn<Part, Integer> assoPartsInventoryCol;

    @FXML
    private TableColumn<Part, Integer> assoPartsPriceCol;

    @FXML
    private TableView<Part> assoPartsTableView;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private static Product productToModify;

    private ObservableList<Part> assoParts = FXCollections.observableArrayList();


    /**
     *@param event onActionAddAssoPart adds associated part to associated part table.
     */
    @FXML
    void onActionAddAssoPart (ActionEvent event) throws IOException{
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItems().get(0);
        boolean duplicatePart = false;

        if (assoParts.size() >= 1) {
            for (Part part : assoParts) {
                if (selectedPart.getId() == part.getId()) {
                    duplicatePart = true;

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setContentText("Part already added to associated part list.");
                    alert.showAndWait();
                    return;

                }
            }
        }
        if (!duplicatePart){
            assoParts.add(selectedPart);
        }
    }
    /**
     *@param event onActionRemoveAssoPart removes associated part from associated part table.
     */
    @FXML
    void onActionRemoveAssoPart (ActionEvent event) throws IOException{
        assoParts.remove((assoPartsTableView.getSelectionModel().getSelectedItems().get(0)));
    }

    /**
     *@param product Pulls selected part from main and prepopulates the screen.
     */
    public void productPicked(Product product) {
        productToModify = product;

        assoParts = productToModify.getAllAssociatedParts();

        assoPartsTableView.setItems(assoParts);

        idTxt.setText(Integer.toString(productToModify.getId()));
        productNameTxt.setText(productToModify.getProductName());
        productPriceTxt.setText(Double.toString(productToModify.getProductPrice()));
        inventoryTxt.setText(Integer.toString(productToModify.getStock()));
        maxTxt.setText(Integer.toString(productToModify.getMax()));
        minTxt.setText(Integer.toString(productToModify.getMin()));

    }
    /**
     * @param event onActionSaveProduct saves product.
     */
    @FXML
    void onActionSaveProduct (ActionEvent event) throws IOException {
        int index = Inventory.getAllProducts().indexOf(productToModify);
        try {
            Product modifiedProduct;

            int id = Integer.parseInt(idTxt.getText());
            String productName = productNameTxt.getText();
            double productPrice = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(inventoryTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Max must be greater than the min!");
                alert.showAndWait();
                return;
            }
            if (stock > max | stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Stock must be between max and min!");
                alert.showAndWait();
                return;
            }

            modifiedProduct = new Product(id, productName, productPrice, stock, min, max);
            for (Part part: assoParts){
                modifiedProduct.addAssociatedPart(part);
            }
            Inventory.updateProduct(index, modifiedProduct);


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter valid values in the text boxes!");
            alert.showAndWait();
        }
    }
    /**
     *@param event onActionDisplayMainMenu brings the user back to the main screen and confirms they want to cancel modifying product.
     */
    @FXML
    void onActionDisplayMainMenu (ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }




    }
    /**
     *@param url
     * @param resourceBundle
     * Initializes the screen and sets the table views.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assoPartsTableView.setItems(assoParts);
        assoPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        assoPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
