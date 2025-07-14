package com.example.cnpm_thuchanh.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int cateId;
    private String name;
    private String description;
    private double price;
    private String imagePath;
    private int soldQuantity;

    public  Product(){}
    public Product(int id, int cateId, String name, String description, double price, String imagePath) {
        this.id = id;
        this.cateId = cateId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }
    // Getters & setters
    public int getId() { return id; }
    public int getCateId() { return cateId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }

    public void setId(int id) { this.id = id; }
    public void setCateId(int cateId) { this.cateId = cateId; }
    public void setName(String name) { this.name = name; }
    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
