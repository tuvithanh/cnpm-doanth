package com.example.cnpm_thuchanh.Model;

import java.io.Serializable;

public class Payment implements Serializable {
    private int id;
    private int orderId;
    private String paymentMethod;
    private String paidAt;
    private double amount;
    private String status;

    public Payment(int id, int orderId, String paymentMethod, String paidAt, double amount, String status) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paidAt = paidAt;
        this.amount = amount;
        this.status = status;
    }

    public Payment(int orderId, String paymentMethod, double amount) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = "Đã thanh toán";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaidAt() { return paidAt; }
    public void setPaidAt(String paidAt) { this.paidAt = paidAt; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
