package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void saveCustomerAddress(CustomerAddress customerAddress) {
        try {
            // Assuming 'Customer_Address' is auto-incremented, you don't need to insert a value for it.
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
