package com.in.patient.model;

public class LabTestModel {
    String BookingId,BookingDate,AmountPaid,Refund,Amount,help;

    public LabTestModel(String bookingId, String bookingDate, String amountPaid, String refund, String amount, String help) {
        BookingId = bookingId;
        BookingDate = bookingDate;
        AmountPaid = amountPaid;
        Refund = refund;
        Amount = amount;
        this.help = help;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
    }

    public String getRefund() {
        return Refund;
    }

    public void setRefund(String refund) {
        Refund = refund;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }
}
