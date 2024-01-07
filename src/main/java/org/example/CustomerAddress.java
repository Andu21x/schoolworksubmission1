package org.example;

public class CustomerAddress {
    private int customerAddressId;
    private int fkCustomerAddress;
    private int fkAddressAddress;

    private String firstName;
    private String lastName;
    private String postcode;

    // Constructors
    public CustomerAddress(int customerAddressId, int fkCustomerAddress, int fkAddressAddress, String firstName, String lastName, String postcode) {
        this.customerAddressId = customerAddressId;
        this.fkCustomerAddress = fkCustomerAddress;
        this.fkAddressAddress = fkAddressAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postcode = postcode;
    }

    // Getters and Setters
    public int getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(int customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public int getFkCustomerAddress() {
        return fkCustomerAddress;
    }

    public void setFkCustomerAddress(int fkCustomerAddress) {
        this.fkCustomerAddress = fkCustomerAddress;
    }

    public int getFkAddressAddress() {
        return fkAddressAddress;
    }

    public void setFkAddressAddress(int fkAddressAddress) {
        this.fkAddressAddress = fkAddressAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    // toString methods
    @Override
    public String toString() {
        return String.format("Customer Address Link ID: %d\nCustomer ID: %d\nAddress ID: %d\n",
                customerAddressId, fkCustomerAddress, fkAddressAddress);
    }

    public String toStringWithAdditionalInfo(String firstName, String lastName, String postcode) {
        return String.format("%sCustomer Name: %s %s\nPostcode: %s\n\n",
                toString(), firstName, lastName, postcode);
    }
}
