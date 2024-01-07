/**
 * Controller class for the CustomerPage FXML, managing user interactions and navigation within the application.
 * Handles actions such as creating customers, displaying customer lists, and navigating to other pages.
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

public class CustomerPageController {

    // Database connection instance
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    // FXML elements
    @FXML private Label DOBFormatLabel;
    @FXML private Button addressPageButton;
    @FXML private Button customerAddressPageButton;
    @FXML private TextArea customerInfoTextArea;
    @FXML private Pane pane;
    @FXML private Button exitButton;
    @FXML private Button customerPageButton;
    @FXML private Button purchasePageButton;
    @FXML private Button CreateCustomerButton;
    @FXML private Button ShowCustomerButton;
    @FXML private Label FirstNameLabel;
    @FXML private Label LastNameLabel;
    @FXML private Label DOBLabel;
    @FXML private Label TelephoneLabel;
    @FXML private Label EmailLabel;
    @FXML private TextField FirstNameTextField;
    @FXML private TextField LastNameTextField;
    @FXML private TextField DOBTextField;
    @FXML private TextField TelephoneTextField;
    @FXML private TextField EmailTextField;

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

    // Handles creating a new customer, saving it to the database, and updating the UI
    @FXML private void handleCreateCustomerButtonAction() {
        // Set the data from the text fields to the Customer object
        Customer customer = new Customer(
                FirstNameTextField.getText(),
                LastNameTextField.getText(),
                DOBTextField.getText(),
                TelephoneTextField.getText(),
                EmailTextField.getText()
        );

        // Save the customer to the database using DatabaseConnection
        databaseConnection.saveCustomer(customer);

        // Clear the text fields after saving
        clearTextFields();
    }

    // Helper method to save customer details to the database (placeholder for actual implementation)
    @FXML private void saveCustomerToDatabase(Customer customer) {
        // Add your logic to save the customer to the database (e.g., using JDBC)
        // For demonstration purposes, let's print the customer details
        System.out.println("Customer created: " + customer);
    }

    // Handles displaying the list of customers
    @FXML private void handleShowCustomerButtonAction() {
        // Retrieve all customers from the database using DatabaseConnection
        List<Customer> customers = databaseConnection.getAllCustomers();

        // Display the customers in the TextArea
        StringBuilder customerInfo = new StringBuilder();
        for (Customer customer : customers) {
            customerInfo.append(customer.toString()).append("\n");
        }
        customerInfoTextArea.setText(customerInfo.toString());
    }

    // Clears the text fields after creating the customer
    @FXML private void clearTextFields() {
        FirstNameTextField.clear();
        LastNameTextField.clear();
        DOBTextField.clear();
        TelephoneTextField.clear();
        EmailTextField.clear();
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
