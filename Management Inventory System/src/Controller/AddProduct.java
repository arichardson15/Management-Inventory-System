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
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField companyOrMachineTxt;

    @FXML
    private Label companyOrMachineLbl;

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
    private Button removeAssoPartBtn;

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
    private ObservableList<Part> assoParts = FXCollections.observableArrayList();


    /**
     *@param event  onActionAddAssoPart add part to the associated parts table.
     */
    @FXML
    void onActionAddAssoPart (ActionEvent event) throws IOException{
       assoParts.add(partsTableView.getSelectionModel().getSelectedItems().get(0));
    }
    /**
     *@param event onActionRemoveAssoPart remove part from the associated parts table.
     */
    @FXML
    void onActionRemoveAssoPart (ActionEvent event) throws IOException{
        assoParts.remove((assoPartsTableView.getSelectionModel().getSelectedItems().get(0)));
    }
    /**
     *@param event onActionSaveProduct saves product
     */
    @FXML
    void onActionSaveProduct (ActionEvent event) throws IOException {

        try {


            int id = (Inventory.getAllProducts().size() + 1);
            String productName = productNameTxt.getText();
            double productPrice = Integer.parseInt(productPriceTxt.getText());
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

            Product product = new Product(id, productName, productPrice, stock, min, max);
            for (Part part: assoParts){
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter valid values in the text boxes!");
            alert.showAndWait();


        }
    }

    /**
     *@param event onActionDisplay Main brings the user to the main menu and confirms they wish to cancel adding product.
     */
    @FXML
    void onActionDisplayMain (ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /**
     *@param location
     * @param resources
     * initializes the screen
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         *@param setItems sets the tables to allParts and assoParts.
         */
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
