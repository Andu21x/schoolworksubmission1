/**
 * Controller class for the CustomerAddressPage FXML, managing user interactions and navigation within the application.
 * Handles actions such as creating customer-address links, displaying customer-address lists, and navigating to other pages.
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
import java.util.List;

public class CustomerAddressPageController {

    // Database connection instance
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    // FXML elements
    @FXML private Pane pane;
    @FXML private Button exitButton;
    @FXML private Button customerPageButton;
    @FXML private Button purchasePageButton;
    @FXML private Button addressPageButton;
    @FXML private TextArea customerAddressInfoTextArea;
    @FXML private Button showCustomerAddressButton;
    @FXML private Label addressIDLabel;
    @FXML private TextField addressIDTextField;
    @FXML private Label customerIDLabel;
    @FXML private TextField customerIDTextField;
    @FXML private Button createLinkButton;
    @FXML private Button customerAddressPageButton;

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

    // Handles displaying the list of customer addresses
    @FXML private void handleShowCustomerAddressListButtonAction() {
        // Retrieve and display all customer addresses
        List<CustomerAddress> customerAddressList = databaseConnection.getCustomerAddressList();
        customerAddressInfoTextArea.clear();

        for (CustomerAddress customerAddress : customerAddressList) {
            // Retrieve additional information
            String firstName = databaseConnection.getCustomerFirstName(customerAddress.getFkCustomerAddress());
            String lastName = databaseConnection.getCustomerLastName(customerAddress.getFkCustomerAddress());
            String postcode = databaseConnection.getAddressPostcode(customerAddress.getFkAddressAddress());

            // Append the information to the text area
            customerAddressInfoTextArea.appendText(customerAddress.toStringWithAdditionalInfo(firstName, lastName, postcode));
        }
    }

    // Displays a single customer address in the text area
    private void showCustomerAddress(CustomerAddress customerAddress) {
        customerAddressInfoTextArea.appendText(customerAddress.toString());
    }

    // Handles creating a new customer-address link, saving it to the database, and updating the UI
    @FXML private void handleCreateLinkButtonAction() {
        try {
            // Retrieve values from text fields
            int customerAddressId = Integer.parseInt(addressIDTextField.getText());
            int fkCustomerAddress = Integer.parseInt(customerIDTextField.getText());
            int fkAddressAddress = Integer.parseInt(addressIDTextField.getText());

            // Retrieve additional information from the database
            String firstName = databaseConnection.getCustomerFirstName(fkCustomerAddress);
            String lastName = databaseConnection.getCustomerLastName(fkCustomerAddress);
            String postcode = databaseConnection.getAddressPostcode(fkAddressAddress);

            // Create CustomerAddress object
            CustomerAddress customerAddress = new CustomerAddress(customerAddressId, fkCustomerAddress, fkAddressAddress, firstName, lastName, postcode);

            // Save the CustomerAddress to the database
            databaseConnection.saveCustomerAddress(customerAddress);

            // Display success message or update UI as needed
            customerAddressInfoTextArea.setText("Customer Address created successfully!");

            // Clear the text fields after saving
            clearTextFields();
        } catch (Exception e) {
            customerAddressInfoTextArea.setText("Error creating Customer Address. Please check input values.");
            e.printStackTrace();
        }
    }

    // Clears the text fields after creating the customer address
    @FXML private void clearTextFields() {
        addressIDTextField.clear();
        customerIDTextField.clear();
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
