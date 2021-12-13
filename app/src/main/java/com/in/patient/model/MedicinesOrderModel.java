package com.in.patient.model;

public class MedicinesOrderModel {

    String OrderId, OrderDate, OrderAddress, AmountPaid;

    public MedicinesOrderModel(String orderId, String orderDate, String orderAddress, String amountPaid) {
        OrderId = orderId;
        OrderDate = orderDate;
        OrderAddress = orderAddress;
        AmountPaid = amountPaid;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
    }
}
