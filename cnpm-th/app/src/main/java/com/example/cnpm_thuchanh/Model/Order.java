package com.example.cnpm_thuchanh.Model;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private int userId;
    private String createdAt;
    private String status;
    private double total;

    public Order() {
    }

    public Order(int id, int userId, String createdAt, String status, double total) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
        this.total = total;
    }

    public Order(int userId, double total) {
        this.userId = userId;
        this.total = total;
        this.status = "Chờ xử lý";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
