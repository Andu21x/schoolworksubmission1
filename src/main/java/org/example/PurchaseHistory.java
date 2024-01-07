package org.example;

import java.sql.Date;

public class PurchaseHistory {

    // Initialise private fields
    private Date purchaseDate;
    private String productName;
    private double purchaseAmount;
    private int customerId;


    // Purchase History Constructor, and its arguments
    public PurchaseHistory(Date purchaseDate, String productName, double purchaseAmount, int customerId) {
        this.purchaseDate = purchaseDate;
        this.productName = productName;
        this.purchaseAmount = purchaseAmount;
        this.customerId = customerId;
    }

    // Getters and Setters
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }
    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // Returns a formatted string representation of the purchase history, with purchase date, product name and purchase amount
    @Override
    public String toString() {
        return String.format("Purchase Date: %s\nProduct Name: %s\nPurchase Amount: %.2f\n",
                purchaseDate, productName, purchaseAmount);
    }

}
