package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button addressPageButton;

    @FXML
    private Button customerAddressPageButton;

    @FXML
    private Pane pane;

    @FXML
    private Button exitButton;

    @FXML
    private Button customerPageButton;

    @FXML
    private Button purchasePageButton;

    @FXML
    private void handleExitButtonAction() {
        Platform.exit(); // Close the JavaFX application
    }

    @FXML
    private void handleCustomerPageButtonAction() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("customer-view-page.fxml"));
            Parent customerPage = loader.load();

            // Set the loaded FXML as the content of the pane
            pane.getChildren().setAll(customerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePurchasePageButtonAction() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("purchase-history-page.fxml"));
            Parent customerPage = loader.load();

            // Set the loaded FXML as the content of the pane
            pane.getChildren().setAll(customerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddressPageButtonAction() {
        try {
            // Load the new FXML file for the address page
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("address-page.fxml"));
            Parent addressPage = loader.load();

            // Set the loaded FXML as the content of the pane
            pane.getChildren().setAll(addressPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCustomerAddressPageButtonAction() {
        try {
            // Load the new FXML file for the customer address page
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("customer-address-page.fxml"));
            Parent customerAddressPage = loader.load();

            // Set the loaded FXML as the content of the pane
            pane.getChildren().setAll(customerAddressPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
