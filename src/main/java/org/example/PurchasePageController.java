package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PurchasePageController {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @FXML
    public Button addressPageButton;

    @FXML
    private TextField CustomerIDTextField;

    @FXML
    private Button customerPageButton;

    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Pane pane;

    @FXML
    private TextArea purchaseInfoTextArea;

    @FXML
    private Button exitButton;

    @FXML
    private Button purchasePageButton;

    @FXML
    private Button ShowPurchaseHistoryButton;

    @FXML
    private TextField PurchaseDateTextField;

    @FXML
    private TextField ProductNameTextField;

    @FXML
    private TextField PurchaseAmountTextField;

    @FXML
    private Label PurchaseDateLabel;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label PurchaseAmountLabel;

    @FXML
    private Label PurchaseFormatLabel;

    @FXML
    private Button CreatePurchaseHistoryButton;


    @FXML
    private void handleExitButtonAction() {
        databaseConnection.closeConnection();
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
            Parent purchaseHistoryPage = loader.load();

            // Set the loaded FXML as the content of the pane
            pane.getChildren().setAll(purchaseHistoryPage);
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
    private void handleCreatePurchaseHistoryButtonAction() {
        try {
            // Retrieve values from text fields
            String dateString = PurchaseDateTextField.getText();
            String productName = ProductNameTextField.getText();
            double purchaseAmount = Double.parseDouble(PurchaseAmountTextField.getText());

            // Retrieve customer ID from the TextField
            int customerId = Integer.parseInt(CustomerIDTextField.getText());

            // Parse the date string to java.sql.Date
            Date purchaseDate = parseDate(dateString);

            // Create PurchaseHistory object
            PurchaseHistory purchaseHistory = new PurchaseHistory(purchaseDate, productName, purchaseAmount, customerId);

            // Save the PurchaseHistory to the database
            databaseConnection.savePurchaseHistory(purchaseHistory, customerId);

            // Display success message or update UI as needed
            purchaseInfoTextArea.setText("PurchaseHistory created successfully!");
        } catch (NumberFormatException | ParseException e) {
            purchaseInfoTextArea.setText("Error creating PurchaseHistory. Please check input values.");
            e.printStackTrace();
        }
    }

    // Helper method to parse date string to java.sql.Date
    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Date(parsedDate.getTime());
    }

    @FXML
    private void handleShowPurchaseHistoryButtonAction() {
        // Retrieve and display all purchase history records
        List<PurchaseHistory> purchaseHistoryList = databaseConnection.getPurchaseHistory();
        purchaseInfoTextArea.clear();

        for (PurchaseHistory purchaseHistory : purchaseHistoryList) {
            showPurchaseHistory(purchaseHistory);
        }
    }

    private void showPurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseInfoTextArea.appendText("Date: " + purchaseHistory.getPurchaseDate() +
                "\nProduct Name: " + purchaseHistory.getProductName() +
                "\nPurchase Amount: " + purchaseHistory.getPurchaseAmount() +
                "\nCustomer ID: " + purchaseHistory.getCustomerId() + "\n\n");
    }
}
