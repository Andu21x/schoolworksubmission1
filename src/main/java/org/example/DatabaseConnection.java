/**
 * DatabaseConnection class responsible for establishing and managing the connection to the MySQL database.
 * It includes methods for saving and retrieving customer, purchase history, address, and customer address information.
 */
package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    // Logger for logging errors and information
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    // Database connection details, including initialising strings, to which we assign the needed credentials
    private static Connection connection;
    private static final String DB_URL = "jdbc:mysql://google/quickstart_db?cloudSqlInstance=schoolwork-101010:europe-west2:quickstart-instance";
    private static final String USER = "quickstart-user";
    private static final String PASSWORD = "user123$";

    /**
     * Constructor to initialize the DatabaseConnection and establish a connection to the MySQL database.
     */
    public DatabaseConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            String jdbcUrl = "jdbc:mysql://google/quickstart_db?cloudSqlInstance=schoolwork-101010:europe-west2:quickstart-instance" +
                    "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false" +
                    "&user=quickstart-user" +
                    "&password=user123$";

            logger.log(Level.INFO, "Connecting to database: " + jdbcUrl);

            // Create a connection to the database
            connection = DriverManager.getConnection(jdbcUrl);

        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Error establishing database connection", e);
        }
    }

    /**
     * Main method for testing the initialization of the DatabaseConnection.
     */
    public static void main(String[] args) {
        // Invoking the initialization method
        new DatabaseConnection();
    }

    /**
     * Returns the established database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Saves a customer object to the database.
     */
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

    /**
     * Implementation of retrieving all customers from the database
     */
    public List<Customer> getAllCustomers() {
        // Create a list to store the customers
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

    /**
     * Saves a purchase history object to the database.
     */
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

    /**
     * Retrieves a list of all purchase history records from the database.
     */
    public List<PurchaseHistory> getPurchaseHistory() {
        // Create a list to store the purchase history records
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

    /**
     * Saves an address object to the database.
     */
    public void saveAddress(Address address) {
        try {
            // Implementation of saving an address to the database
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
        }
    }

    /**
     * Retrieves a list of all addresses from the database.
     */
    public List<Address> getAddressList() {
        List<Address> addressList = new ArrayList<>();
        try {
            // Implementation of retrieving all addresses from the database
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
        }
        return addressList;
    }

    /**
     * Saves a customer address object to the database.
     */
    public void saveCustomerAddress(CustomerAddress customerAddress) {
        try {
            // Implementation of saving a customer address to the database
            String sql = "INSERT INTO Customer_Address (customer_ID, address_ID) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, customerAddress.getFkCustomerAddress());
                statement.setInt(2, customerAddress.getFkAddressAddress());
                statement.executeUpdate();

                // If 'Customer_Address' is auto-incremented, retrieve the generated key
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        customerAddress.setCustomerAddressId(generatedId);
                    } else {
                        throw new SQLException("Error getting generated key for Customer_Address");
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving customer address", e);
        }
    }

    /**
     * Retrieves a list of all customer addresses with additional details from the database.
     */
    public List<CustomerAddress> getCustomerAddressList() {
        List<CustomerAddress> customerAddressList = new ArrayList<>();
        try {
            String sql = "SELECT ca.Customer_Address, ca.customer_ID, ca.address_ID, c.firstName, c.lastName, a.postcode " +
                    "FROM Customer_Address ca " +
                    "JOIN Customer c ON ca.customer_ID = c.customer_id " +
                    "JOIN Address a ON ca.address_ID = a.address_id";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int customerAddressId = resultSet.getInt("Customer_Address");
                        int fkCustomerAddress = resultSet.getInt("customer_ID");
                        int fkAddressAddress = resultSet.getInt("address_ID");
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String postcode = resultSet.getString("postcode");

                        customerAddressList.add(new CustomerAddress(customerAddressId, fkCustomerAddress, fkAddressAddress, firstName, lastName, postcode));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all customer addresses with details", e);
        }
        return customerAddressList;
    }

    /**
     * Retrieves the first name of a customer by their ID from the database.
     */
    public String getCustomerFirstName(int customerId) {
        try {
            String sql = "SELECT firstName FROM Customer WHERE customer_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("firstName");
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving customer first name", e);
        }
        return null;
    }

    /**
     * Retrieves the last name of a customer by their ID from the database.
     */
    public String getCustomerLastName(int customerId) {
        try {
            String sql = "SELECT lastName FROM Customer WHERE customer_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("lastName");
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving customer last name", e);
        }
        return null;
    }

    /**
     * Retrieves the postcode of an address by its ID from the database.
     */
    public String getAddressPostcode(int addressId) {
        try {
            String sql = "SELECT postcode FROM Address WHERE address_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, addressId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("postcode");
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving address postcode", e);
        }
        return null;
    }

    /**
     * Closes the database connection.
     */
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
