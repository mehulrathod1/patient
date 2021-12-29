package com.in.patient.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("Total Price")
	private int totalPrice;

	@SerializedName("Discount")
	private String discount;

	@SerializedName("Price Items")
	private String priceItems;

	@SerializedName("Item Price")
	private int itemPrice;

	@SerializedName("Order Details")
	private List<OrderDetailsItem> orderDetails;

	@SerializedName("Delivery Charge")
	private String deliveryCharge;

	public int getTotalPrice(){
		return totalPrice;
	}

	public String getDiscount(){
		return discount;
	}

	public String getPriceItems(){
		return priceItems;
	}

	public int getItemPrice(){
		return itemPrice;
	}

	public List<OrderDetailsItem> getOrderDetails(){
		return orderDetails;
	}

	public String getDeliveryCharge(){
		return deliveryCharge;
	}
}