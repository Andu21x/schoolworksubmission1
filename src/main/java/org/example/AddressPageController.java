package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AddressPageController {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @FXML
    private Button customerAddressPageButton;

    @FXML
    private Pane pane;

    @FXML
    private TextArea addressInfoTextArea;

    @FXML
    private Button exitButton;

    @FXML
    private Button addressPageButton;

    @FXML
    private Button purchasePageButton;

    @FXML
    private Button showAddressListButton;

    @FXML
    private TextField streetAddressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField postcodeTextField;

    @FXML
    private Label streetAddressLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label postcodeLabel;

    @FXML
    private Button createAddressButton;

    @FXML
    private Button customerPageButton;

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
    private void handleCreateAddressButtonAction() {
        try {
            // Retrieve values from text fields
            String streetAddress = streetAddressTextField.getText();
            String city = cityTextField.getText();
            String state = stateTextField.getText();
            String postcode = postcodeTextField.getText();

            // Create Address object
            Address address = new Address(streetAddress, city, state, postcode);

            // Save the Address to the database
            databaseConnection.saveAddress(address);

            // Display success message or update UI as needed
            addressInfoTextArea.setText("Address created successfully!");
        } catch (Exception e) {
            addressInfoTextArea.setText("Error creating Address. Please check input values.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowAddressListButtonAction() {
        // Retrieve and display all addresses
        List<Address> addressList = databaseConnection.getAddressList();
        addressInfoTextArea.clear();

        for (Address address : addressList) {
            showAddress(address);
        }
    }

    private void showAddress(Address address) {
        addressInfoTextArea.appendText("Address ID: " + getAddressId(address) +
                "\nStreet Address: " + address.getStreetAddress() +
                "\nCity: " + address.getCity() +
                "\nState: " + address.getState() +
                "\nPostcode: " + address.getPostcode() + "\n\n");
    }

    private int getAddressId(Address address) {
        String sql = "SELECT address_ID FROM Address WHERE street_address = ? AND city = ? AND state = ? AND postcode = ?";
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, address.getStreetAddress());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getState());
            statement.setString(4, address.getPostcode());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("address_ID");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1; // Return a default value or handle the case when the address_ID is not found
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
