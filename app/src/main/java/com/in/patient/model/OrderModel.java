package com.in.patient.model;

public class OrderModel {

    String ProductName, OrderDate, AmountPaid, OrderStatus, OrderId,PaymentStatus, OrderDetail, DeliveryAddress;

    public OrderModel(String productName, String orderDate, String amountPaid, String orderStatus, String orderId, String paymentStatus, String orderDetail, String deliveryAddress) {
        ProductName = productName;
        OrderDate = orderDate;
        AmountPaid = amountPaid;
        OrderStatus = orderStatus;
        OrderId = orderId;
        PaymentStatus = paymentStatus;
        OrderDetail = orderDetail;
        DeliveryAddress = deliveryAddress;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        OrderDetail = orderDetail;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }
}
