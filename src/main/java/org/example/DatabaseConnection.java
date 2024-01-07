package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Class.*;

public class DatabaseConnection {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private static Connection connection;

    public DatabaseConnection() {
        try {
            // Load the MySQL JDBC driver
            forName("com.mysql.cj.jdbc.Driver");

/*            String jdbcUrl = "jdbc:mysql://localhost:3306/schoolwork-101010:europe-west2:quickstart-instance" +
                    "?cloudSqlInstance=quickstart-instance" +
                    "&socketFactory=com.google.cloud.sql.mysql.SocketFactory" +
                    "&user=quickstart-user" +
                    "&password=user123$";*/

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


    public static void main(String[] args) {
        // Example of invoking the initialization method
        new DatabaseConnection();
    }
}
