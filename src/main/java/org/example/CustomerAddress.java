package org.example;

public class CustomerAddress {
    private int customerAddressId;
    private int fkCustomerAddress;
    private int fkAddressAddress;

    // Constructors
    public CustomerAddress(int customerAddressId, int fkCustomerAddress, int fkAddressAddress) {
        this.customerAddressId = customerAddressId;
        this.fkCustomerAddress = fkCustomerAddress;
        this.fkAddressAddress = fkAddressAddress;
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

    // toString method
    @Override
    public String toString() {
        return String.format("Customer Address ID: %d\nFK Customer Address: %d\nFK Address Address: %d\n",
                customerAddressId, fkCustomerAddress, fkAddressAddress);
    }
}
