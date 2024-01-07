package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Class.*;

public class DatabaseConnection {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private static Connection connection;
    private static final String DB_URL = "jdbc:mysql://google/quickstart_db?cloudSqlInstance=schoolwork-101010:europe-west2:quickstart-instance";
    private static final String USER = "quickstart-user";
    private static final String PASSWORD = "user123$";


    public DatabaseConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            String jdbcUrl = "jdbc:mysql://google/quickstart_db?cloudSqlInstance=schoolwork-101010:europe-west2:quickstart-instance" +
                    "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false" +
                    "&user=quickstart-user" +
                    "&password=user123$";


            connection = DriverManager.getConnection(jdbcUrl);

        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error establishing database connection", e);
        }
    }

    public static void main(String[] args) {
        // Example of invoking the initialization method
        new DatabaseConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public void saveCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO Customer (firstName, lastName, doB, telephone, email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getDoB());
                statement.setString(4, customer.getTelephone());
                statement.setString(5, customer.getEmail());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving customer", e);
        }
    }

    public Customer getCustomerById(int customerId) {
        try {
            String sql = "SELECT * FROM Customer WHERE customer_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve all necessary fields from the database
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String doB = resultSet.getString("doB");
                        String telephone = resultSet.getString("telephone");
                        String email = resultSet.getString("email");

                        // Create and return the Customer object
                        return new Customer(firstName, lastName, doB, telephone, email);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving customer by ID", e);
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Customer";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String doB = resultSet.getString("doB");
                        String telephone = resultSet.getString("telephone");
                        String email = resultSet.getString("email");

                        customers.add(new Customer(firstName, lastName, doB, telephone, email));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all customers", e);
        }

        return customers;
    }

    public void savePurchaseHistory(PurchaseHistory purchaseHistory, int customerId) {
        try {
            String sql = "INSERT INTO PurchaseHistory (purchaseDate, productName, purchaseAmount, customer_ID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, purchaseHistory.getPurchaseDate());
                statement.setString(2, purchaseHistory.getProductName());
                statement.setDouble(3, purchaseHistory.getPurchaseAmount());
                statement.setInt(4, customerId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving purchase history", e);
        }
    }


    public List<PurchaseHistory> getPurchaseHistory() {
        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();

        try {
            String sql = "SELECT purchaseDate, productName, purchaseAmount, customer_ID FROM PurchaseHistory";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Date purchaseDate = resultSet.getObject("purchaseDate", Date.class);
                        String productName = resultSet.getString("productName");
                        double purchaseAmount = resultSet.getDouble("purchaseAmount");
                        int customer_ID = resultSet.getInt("customer_ID");

                        purchaseHistoryList.add(new PurchaseHistory(purchaseDate, productName, purchaseAmount, customer_ID));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all purchase history", e);
        }

        return purchaseHistoryList;
    }

    public void saveAddress(Address address) {
        try {
            // Assuming you have a table named "Address" with appropriate columns
            String query = "INSERT INTO Address (street_address, city, state, postcode) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, address.getStreetAddress());
                preparedStatement.setString(2, address.getCity());
                preparedStatement.setString(3, address.getState());
                preparedStatement.setString(4, address.getPostcode());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public List<Address> getAddressList() {
        List<Address> addressList = new ArrayList<>();
        try {
            // Assuming you have a table named "Address" with appropriate columns
            String query = "SELECT * FROM Address";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String streetAddress = resultSet.getString("street_address");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");
                    String postcode = resultSet.getString("postcode");

                    Address address = new Address(streetAddress, city, state, postcode);
                    addressList.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return addressList;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing database connection", e);
        }
    }
}
