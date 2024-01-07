/**
 * Controller class for the Hello FXML, managing user interactions and navigation within the application.
 * Handles actions such as navigating to customer, purchase history, address, and customer address pages.
 */
package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HelloController {

    // FXML elements
    @FXML private Button addressPageButton;
    @FXML private Button customerAddressPageButton;
    @FXML private Pane pane;
    @FXML private Button exitButton;
    @FXML private Button customerPageButton;
    @FXML private Button purchasePageButton;

    // Handles the exit button action, closing the JavaFX application
    @FXML private void handleExitButtonAction() {
        Platform.exit();
    }

    // Handles navigation to the customer page
    @FXML private void handleCustomerPageButtonAction() {
        loadFXML("customer-view-page.fxml");
    }

    // Handles navigation to the purchase history page
    @FXML private void handlePurchasePageButtonAction() {
        loadFXML("purchase-history-page.fxml");
    }

    // Handles navigation to the address page
    @FXML private void handleAddressPageButtonAction() {
        loadFXML("address-page.fxml");
    }

    // Handles navigation to the customer address page
    @FXML private void handleCustomerAddressPageButtonAction() {
        loadFXML("customer-address-page.fxml");
    }

    // Helper method to load FXML file and set it as the content of the pane
    private void loadFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFileName));
            Parent page = loader.load();
            pane.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
