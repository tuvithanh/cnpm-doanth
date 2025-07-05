package com.example.cnpm_thuchanh.Model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int id;
    private int cartId;
    private int productId;
    private int quantity;
    private Product product; // 👉 để hiển thị tên, ảnh, giá

    public CartItem() {}

    public CartItem(int id, int cartId, int productId, int quantity) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public int getCartId() {
        return cartId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public boolean isEmpty() {
        return product == null || quantity <= 0;
    }

}
