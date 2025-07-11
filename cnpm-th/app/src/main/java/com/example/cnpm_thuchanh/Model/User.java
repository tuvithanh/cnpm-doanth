package com.example.cnpm_thuchanh.Model;

import java.io.Serializable;
public class User implements Serializable {
    private int id;
    private String name;
    private int phone;
    private String username;
    private String password;
    private String email;
    private String address;
    private String about;
    private String role;
    private String favorites;

    public User() {
    }

    public User(int id, String name, int phone, String username, String password,
                String email, String address, String about, String role, String favorites) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.about = about;
        this.role = role;
        this.favorites = favorites;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getPhone() { return phone; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getAbout() { return about; }
    public String getRole() { return role; }
    public String getFavorites() { return favorites; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(int phone) { this.phone = phone; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setAbout(String about) { this.about = about; }
    public void setRole(String role) { this.role = role; }
    public void setFavorites(String favorites) { this.favorites = favorites; }
}
