package org.example;

public class Address {

    // Initialise private fields
    private String streetAddress;
    private String city;
    private String state;
    private String postcode;

    //Address Constructor, and its arguments
    public Address(String streetAddress, String city, String state, String postcode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    // Getters and Setters
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    // Returns a formatted string representation of the address with street, city, state, and postcode.
    @Override
    public String toString() {
        return String.format("Street Address: %s\nCity: %s\nState: %s\nPostcode: %s\n",
                streetAddress, city, state, postcode);
    }
}
