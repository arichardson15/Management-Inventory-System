package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 *Declares global variables.
 */
public class ModifyPart implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField companyOrMachineTxt;

    @FXML
    private Label companyOrMachineLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField stockTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private RadioButton inhouseRbtn;

    @FXML
    private RadioButton outsourcedRbtn;
    private static Part partToModify;
    /**
     *@param part pulls selected part from main to prepopulate fields and sets form to inHouse or outsourced.
     */
    public void partPicked(Part part) {
        partToModify = part;
        if (partToModify instanceof InHouse){
            companyOrMachineLbl.setText("Machine ID");
            inhouseRbtn.setSelected(true);
            companyOrMachineTxt.setText(Integer.toString(((InHouse) partToModify).getMachineId()));
        } else {
            companyOrMachineLbl.setText("Company Name");
            companyOrMachineTxt.setPromptText("Company Name");
            outsourcedRbtn.setSelected(true);
            companyOrMachineTxt.setText(((Outsourced)partToModify).getCompanyName());
        }

        idTxt.setText(Integer.toString(partToModify.getId()));
        partNameTxt.setText(partToModify.getPartName());
        priceTxt.setText(Double.toString(partToModify.getPrice()));
        stockTxt.setText(Integer.toString(partToModify.getStock()));
        maxTxt.setText(Integer.toString(partToModify.getMax()));
        minTxt.setText(Integer.toString(partToModify.getMin()));


    }
    /**
     *@param event onActionSavePart Saves part.
     */
    @FXML
    void onActionSavePart (ActionEvent event) throws IOException {
        int index = Inventory.getAllParts().indexOf(partToModify);

        try {

            Part modifiedPart;

            int id = partToModify.getId();
            String partName = partNameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(stockTxt.getText());
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

            if (partToModify instanceof InHouse) {
                int machineID = Integer.parseInt(companyOrMachineTxt.getText());

                modifiedPart = new InHouse(
                        id,
                        partName,
                        price,
                        stock,
                        min,
                        max,
                        machineID);
            } else {
                String companyName = companyOrMachineTxt.getText();
                modifiedPart = new Outsourced(
                        id,
                        partName,
                        price,
                        stock,
                        min,
                        max,
                        companyName);
            }
            Inventory.updatePart(index, modifiedPart);

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter valid values in the text boxes!");
            alert.showAndWait();
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     *@param event onActionDisplayMainMenu brings user to the main screen and confirms they wish to cancel modifying part.
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
     *@param event onActionOutsourced sets form to outsourced
     */
    public void OnActionOutsourced(ActionEvent event) throws IOException{
        companyOrMachineLbl.setText("Company Name");
        companyOrMachineTxt.setPromptText("Company Name");
    }
    /**
     *@param event onActionInHouse sets form to inHouse.
     */
    public void OnActionInHouse(ActionEvent event) throws IOException{
        companyOrMachineLbl.setText("Machine ID");
        companyOrMachineTxt.setPromptText("Machine ID");
    }

    /**
     *@param resourceBundle
     * @param url
     * initializes the screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
