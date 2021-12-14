package com.in.patient.model;

public class ProductModel {

    String ProductName, description, Price;

    public ProductModel(String productName, String description, String price) {
        ProductName = productName;
        this.description = description;
        Price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
