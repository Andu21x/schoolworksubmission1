package org.example;

public class Customer {

    // Initialise private fields
    private String firstName;
    private String lastName;
    private String dob;
    private String telephone;
    private String email;

    //Customer Constructor, and its arguments
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
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns a formatted string representation of the customer, with first name, last name, date of birth, telephone and email
    @Override
    public String toString() {
        return String.format("Name: %s %s\nDate of Birth: %s\nTelephone: %s\nEmail: %s\n",
                firstName, lastName, dob, telephone, email);
    }
}
