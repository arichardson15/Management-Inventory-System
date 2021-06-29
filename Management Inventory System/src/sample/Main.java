package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     *
     * @param args add sample inhouse and outsourced parts with special IDs.
     */
    public static void main(String[] args) {
        Part part1 = new InHouse((Inventory.assignPartId()), "Left Joint Piece", 15, 10, 0, 40, 34);
        Part part2 = new InHouse((Inventory.assignPartId()), "Right Joint Piece", 12, 20, 0, 40, 58);
        Part part3 = new Outsourced((Inventory.assignPartId()), "Rotator", 13, 15, 0, 40, "Apple");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

/**
 * @param product1 creates Robot product and sets unique ID.
 */
        Product product1 = new Product((Inventory.assignProductId()), "Robot", 100.00, 100, 0, 50);

        Inventory.addProduct((product1));

        launch(args);
    }
}
