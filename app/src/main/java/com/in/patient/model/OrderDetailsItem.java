package com.in.patient.model;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsItem{

	@SerializedName("Medicine Name")
	private String medicineName;

	@SerializedName("Price")
	private int price;

	@SerializedName("Product Qty")
	private String productQty;

	public String getMedicineName(){
		return medicineName;
	}

	public int getPrice(){
		return price;
	}

	public String getProductQty(){
		return productQty;
	}
}