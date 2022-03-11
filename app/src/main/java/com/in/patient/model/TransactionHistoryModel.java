package com.in.patient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryModel {
    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    List<TransactionHistoryData> transactionHistoryData = new ArrayList<>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TransactionHistoryData> getTransactionHistoryData() {
        return transactionHistoryData;
    }

    public void setTransactionHistoryData(List<TransactionHistoryData> transactionHistoryData) {
        this.transactionHistoryData = transactionHistoryData;
    }

    public static class TransactionHistoryData {

        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("txn_id")
        @Expose
        String txn_id;

        @SerializedName("amount")
        @Expose
        String amount;

        @SerializedName("payment_status")
        @Expose
        String payment_status;

        @SerializedName("remarks")
        @Expose
        String remarks;

        @SerializedName("txn_date")
        @Expose
        String txn_date;

        public TransactionHistoryData(String id, String txn_id, String amount, String payment_status, String remarks, String txn_date) {
            this.id = id;
            this.txn_id = txn_id;
            this.amount = amount;
            this.payment_status = payment_status;
            this.remarks = remarks;
            this.txn_date = txn_date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTxn_id() {
            return txn_id;
        }

        public void setTxn_id(String txn_id) {
            this.txn_id = txn_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getTxn_date() {
            return txn_date;
        }

        public void setTxn_date(String txn_date) {
            this.txn_date = txn_date;
        }
    }
}
