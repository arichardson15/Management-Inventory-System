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

public class AddPart implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField companyOrMachineTxt;

    @FXML
    private Label companyOrMachineLbl;

    @FXML
    public TextField idTxt;

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
    private RadioButton inHouseRbtn;

    @FXML
    private RadioButton outsourcedRbtn;

    /**
     * @param event onActionSavePart saves part
     */
    @FXML
    void onActionSavePart (ActionEvent event) throws IOException {

        try {


            int id = (Inventory.getAllParts().size() + 1);
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

            if (inHouseRbtn.isSelected()) {


                int machineID = Integer.parseInt(companyOrMachineTxt.getText());

                Inventory.addPart(new InHouse(id, partName, price, stock, min, max, machineID));

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
              }

            else if (outsourcedRbtn.isSelected())  {


                String companyName = companyOrMachineTxt.getText();

                Inventory.addPart(new Outsourced(id, partName, price, stock, min, max, companyName));

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }
        /**
         *@param check checks for number format exception.
         */
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter valid values in the text boxes!");
            alert.showAndWait();
        }



    }
    /**
     *@param event onActionDisplayMainMenu brings user back to main as well as confirming that they wish to cancel adding a new part.
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
     *@param event OnActionInHouse changes the form to inHouse.
     */
    @FXML
    void onActionInHouse (ActionEvent event) throws IOException{
        companyOrMachineLbl.setText("Machine ID");
        companyOrMachineTxt.setPromptText("Machine ID");

    }
    /**
     *@param event onActionOutsourced changes the form to outsourced.
     */
    @FXML
    void onActionOutsourced (ActionEvent event) throws IOException{
        companyOrMachineLbl.setText("Company Name");
        companyOrMachineTxt.setPromptText("Company Name");

    }
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
