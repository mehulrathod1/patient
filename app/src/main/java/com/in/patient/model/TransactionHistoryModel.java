package com.in.patient.model;

public class TransactionHistoryModel {
    String TransactionId,TransactionDate,OrderId,TransactionAmount;

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        TransactionAmount = transactionAmount;
    }

    public TransactionHistoryModel(String transactionId, String transactionDate, String orderId, String transactionAmount) {
        TransactionId = transactionId;
        TransactionDate = transactionDate;
        OrderId = orderId;
        TransactionAmount = transactionAmount;
    }
}
