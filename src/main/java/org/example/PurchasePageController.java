/**
 * Controller class for the PurchasePage FXML, managing user interactions and navigation within the application.
 * Handles actions such as creating purchase history records, displaying purchase history lists, and navigating to other pages.
 */
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

    // Database connection instance
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    // FXML elements
    @FXML private Button addressPageButton;
    @FXML private Button customerAddressPageButton;
    @FXML private TextField CustomerIDTextField;
    @FXML private Button customerPageButton;
    @FXML private Label CustomerIDLabel;
    @FXML private Pane pane;
    @FXML private TextArea purchaseInfoTextArea;
    @FXML private Button exitButton;
    @FXML private Button purchasePageButton;
    @FXML private Button ShowPurchaseHistoryButton;
    @FXML private TextField PurchaseDateTextField;
    @FXML private TextField ProductNameTextField;
    @FXML private TextField PurchaseAmountTextField;
    @FXML private Label PurchaseDateLabel;
    @FXML private Label ProductNameLabel;
    @FXML private Label PurchaseAmountLabel;
    @FXML private Label PurchaseFormatLabel;
    @FXML private Button CreatePurchaseHistoryButton;

    // Handles the exit button action, closing the database connection and the JavaFX application
    @FXML private void handleExitButtonAction() {
        databaseConnection.closeConnection();
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

    // Handles creating a new purchase history record, saving it to the database, and updating the UI
    @FXML private void handleCreatePurchaseHistoryButtonAction() {
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
        java.util.Date parsedDate = dateFormat.parse(dateString);
        return new Date(parsedDate.getTime());
    }

    // Handles displaying the list of purchase history records
    @FXML private void handleShowPurchaseHistoryButtonAction() {
        // Retrieve and display all purchase history records, and put it in a list
        List<PurchaseHistory> purchaseHistoryList = databaseConnection.getPurchaseHistory();
        purchaseInfoTextArea.clear();

        for (PurchaseHistory purchaseHistory : purchaseHistoryList) {
            showPurchaseHistory(purchaseHistory);
        }
    }

    // Displays a single purchase history record in the text area
    private void showPurchaseHistory(PurchaseHistory purchaseHistory) {
        purchaseInfoTextArea.appendText("Date: " + purchaseHistory.getPurchaseDate() +
                "\nProduct Name: " + purchaseHistory.getProductName() +
                "\nPurchase Amount: " + purchaseHistory.getPurchaseAmount() +
                "\nCustomer ID: " + purchaseHistory.getCustomerId() + "\n\n");
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
