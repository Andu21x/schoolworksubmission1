package org.example;

public class Customer {
    private String firstName;
    private String lastName;
    private String dob;
    private String telephone;
    private String email;

    // Constructors
    public Customer() {
    }

    public Customer(String firstName, String lastName, String dob, String telephone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.telephone = telephone;
        this.email = email;
    }


    // Getters and Setters
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

    public String getDoB() {
        return dob;
    }

    public void setDoB(String doB) {
        dob = doB;
    }

    public String getTelephone() {
        return telephone; // Change from Telephone to telephone
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone; // Change from Telephone to telephone
    }

    public String getEmail() {
        return email; // Change from Email to email
    }

    public void setEmail(String email) {
        this.email = email; // Change from Email to email
    }
}
