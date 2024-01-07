/**
 * Controller class for the AddressPage FXML, managing user interactions and navigation within the application.
 * Handles actions such as creating addresses, displaying address lists, and navigating to other pages.
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddressPageController {

    // Database connection instance
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    // FXML elements
    @FXML private Button customerAddressPageButton;
    @FXML private Pane pane;
    @FXML private TextArea addressInfoTextArea;
    @FXML private Button exitButton;
    @FXML private Button addressPageButton;
    @FXML private Button purchasePageButton;
    @FXML private Button showAddressListButton;
    @FXML private TextField streetAddressTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField stateTextField;
    @FXML private TextField postcodeTextField;
    @FXML private Label streetAddressLabel;
    @FXML private Label cityLabel;
    @FXML private Label stateLabel;
    @FXML private Label postcodeLabel;
    @FXML private Button createAddressButton;
    @FXML private Button customerPageButton;

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

    // Handles creating a new address, saving it to the database, and updating the UI
    @FXML private void handleCreateAddressButtonAction() {
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

    // Handles displaying the list of addresses
    @FXML private void handleShowAddressListButtonAction() {
        // Retrieve and display all addresses
        List<Address> addressList = databaseConnection.getAddressList();

        // Clear the TextArea before displaying
        addressInfoTextArea.clear();

        for (Address address : addressList) {
            showAddress(address);
        }
    }

    // Displays address in the text area
    private void showAddress(Address address) {
        addressInfoTextArea.appendText("Address ID: " + getAddressId(address) +
                "\nStreet Address: " + address.getStreetAddress() +
                "\nCity: " + address.getCity() +
                "\nState: " + address.getState() +
                "\nPostcode: " + address.getPostcode() + "\n\n");
    }

    // Retrieves the ID of an address from the database
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
