package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.CartItem;
import com.example.cnpm_thuchanh.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartItemDao {

    private final SQLiteDatabase db;

    public CartItemDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm hoặc cập nhật sản phẩm trong giỏ hàng
    public void addOrUpdateItem(int cartId, int productId, int quantity) {
        // Kiểm tra nếu sản phẩm đã có trong giỏ
        String query = "SELECT id, quantity FROM CartItem WHERE cart_id=? AND product_id=?";
        var cursor = db.rawQuery(query, new String[]{String.valueOf(cartId), String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            // Đã có sản phẩm → cập nhật số lượng
            int itemId = cursor.getInt(0);
            int oldQty = cursor.getInt(1);
            ContentValues values = new ContentValues();
            values.put("quantity", oldQty + quantity);
            db.update("CartItem", values, "id = ?", new String[]{String.valueOf(itemId)});
        } else {
            // Chưa có → thêm mới
            ContentValues values = new ContentValues();
            values.put("cart_id", cartId);
            values.put("product_id", productId);
            values.put("quantity", quantity);
            db.insert("CartItem", null, values);
        }
        cursor.close();
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeItem(int cartId, int productId) {
        db.delete("CartItem", "cart_id=? AND product_id=?", new String[]{String.valueOf(cartId), String.valueOf(productId)});
    }

    // Cập nhật số lượng trực tiếp
    public void updateQuantity(int itemId, int quantity) {
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("CartItem", values, "id = ?", new String[]{String.valueOf(itemId)});
    }
    public List<CartItem> getItemsByCartId(int cartId) {
        List<CartItem> items = new ArrayList<>();
        String query = "SELECT ci.id, ci.quantity, p.id, p.name, p.description, p.price, p.imagepath " +
                "FROM CartItem ci " +
                "JOIN Product p ON ci.product_id = p.id " +
                "WHERE ci.cart_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cartId)});
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setId(cursor.getInt(0));
                item.setQuantity(cursor.getInt(1));

                Product product = new Product();
                product.setId(cursor.getInt(2));
                product.setName(cursor.getString(3));
                product.setDescription(cursor.getString(4));
                product.setPrice(cursor.getDouble(5));
                product.setImagePath(cursor.getString(6));

                item.setProduct(product);
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }
    // Xoá 1 dòng CartItem theo ID
    public void deleteItem(int itemId) {
        db.delete("CartItem", "id = ?", new String[]{String.valueOf(itemId)});
    }
    public void clearCart(int cartId) {
        db.delete("CartItem", "cart_id = ?", new String[]{String.valueOf(cartId)});
    }


}
