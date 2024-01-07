package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CustomerPageController {

    @FXML
    private TextArea customerInfoTextArea;

    @FXML
    private Pane pane;

    @FXML
    private Button exitButton;

    @FXML
    private Button customerPageButton;

    @FXML
    private Button purchasePageButton;

    @FXML
    private Button CreateCustomerButton;

    @FXML
    private Button ShowCustomerButton;

    @FXML
    private Label FirstNameLabel;

    @FXML
    private Label LastNameLabel;

    @FXML
    private Label DOBLabel;

    @FXML
    private Label TelephoneLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private TextField FirstNameTextField;

    @FXML
    private TextField LastNameTextField;

    @FXML
    private TextField DOBTextField;

    @FXML
    private TextField TelephoneTextField;

    @FXML
    private TextField EmailTextField;

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
    private void handleCreateCustomerButtonAction() {
        // Set the data from the text fields to the Customer object
        Customer customer = new Customer(
                FirstNameTextField.getText(),
                LastNameTextField.getText(),
                DOBTextField.getText(),
                TelephoneTextField.getText(),
                EmailTextField.getText()
        );

        // Save the customer to the database using DatabaseConnection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.saveCustomer(customer);

        // Optionally, you can clear the text fields after saving
        clearTextFields();
    }

    @FXML
    private void saveCustomerToDatabase(Customer customer) {
        // Add your logic to save the customer to the database (e.g., using JDBC)
        // For demonstration purposes, let's print the customer details
        System.out.println("Customer created: " + customer);
    }

    @FXML
    private void handleShowCustomerButtonAction() {
        // Retrieve all customers from the database using DatabaseConnection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        List<Customer> customers = databaseConnection.getAllCustomers();

        // Display the customers in the TextArea
        StringBuilder customerInfo = new StringBuilder();
        for (Customer customer : customers) {
            customerInfo.append(customer.toString()).append("\n");
        }
        customerInfoTextArea.setText(customerInfo.toString());
    }
    @FXML
    private void clearTextFields() {
        // Clear the text fields after creating the customer
        FirstNameTextField.clear();
        LastNameTextField.clear();
        DOBTextField.clear();
        TelephoneTextField.clear();
        EmailTextField.clear();
    }

}
