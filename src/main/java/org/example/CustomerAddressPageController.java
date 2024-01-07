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

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @FXML
    private Pane pane;

    @FXML
    private Button exitButton;

    @FXML
    private Button customerPageButton;

    @FXML
    private Button purchasePageButton;

    @FXML
    private Button addressPageButton;

    @FXML
    private TextArea customerAddressInfoTextArea;

    @FXML
    private Button showCustomerAddressButton;

    @FXML
    private Label addressIDLabel;

    @FXML
    private TextField addressIDTextField;

    @FXML
    private Label customerIDLabel;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private Button createLinkButton;

    @FXML
    private Button customerAddressPageButton;



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
    private void handleShowCustomerAddressListButtonAction() {
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

    private void showCustomerAddress(CustomerAddress customerAddress) {
        customerAddressInfoTextArea.appendText(customerAddress.toString());
    }

    @FXML
    private void handleCreateLinkButtonAction() {
        try {
            // Retrieve values from text fields
            int customerAddressId = Integer.parseInt(addressIDTextField.getText());
            int fkCustomerAddress = Integer.parseInt(customerIDTextField.getText());
            int fkAddressAddress = Integer.parseInt(addressIDTextField.getText()); // Assuming the same text field for both IDs

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

            // Optionally, you can clear the text fields after saving
            clearTextFields();
        } catch (Exception e) {
            customerAddressInfoTextArea.setText("Error creating Customer Address. Please check input values.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clearTextFields() {
        // Clear the text fields after creating the customer address
        addressIDTextField.clear();
        customerIDTextField.clear();
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
