package com.in.patient.model;

public class ServiceCategoryModel {
    String CategoryName, Amount, description;

    public ServiceCategoryModel(String categoryName, String amount, String description) {
        CategoryName = categoryName;
        Amount = amount;
        this.description = description;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

