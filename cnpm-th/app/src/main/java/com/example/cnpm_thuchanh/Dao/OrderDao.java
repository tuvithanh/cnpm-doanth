package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private final SQLiteDatabase db;

    public OrderDao(Context context) {
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public int insertOrder(int userId, double total) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("total", total);
        values.put("status", "Chờ xử lý");
        long id = db.insert("`Order`", null, values);
        return (int) id;
    }


    public Order getOrderById(int orderId) {
        Cursor cursor = db.rawQuery("SELECT * FROM `Order` WHERE id = ?", new String[]{String.valueOf(orderId)});
        if (cursor.moveToFirst()) {
            Order order = new Order();
            order.setId(cursor.getInt(0));
            order.setUserId(cursor.getInt(1));
            order.setCreatedAt(cursor.getString(2));
            order.setStatus(cursor.getString(3));
            order.setTotal(cursor.getDouble(4));
            cursor.close();
            return order;
        }
        return null;
    }
    // Lấy danh sách tất cả hoá đơn
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM `Order`", null);
        while (c.moveToNext()) {
            Order order = new Order();
            order.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            order.setUserId(c.getInt(c.getColumnIndexOrThrow("user_id")));
            order.setCreatedAt(c.getString(c.getColumnIndexOrThrow("created_at")));
            order.setStatus(c.getString(c.getColumnIndexOrThrow("status")));
            order.setTotal(c.getDouble(c.getColumnIndexOrThrow("total")));
            list.add(order);
        }
        c.close();
        return list;
    }

    // Xoá hoá đơn
    public void deleteOrder(int orderId) {
        db.delete("OrderDetail", "order_id=?", new String[]{String.valueOf(orderId)}); // Xoá chi tiết trước
        db.delete("Payment", "order_id=?", new String[]{String.valueOf(orderId)});     // Xoá thanh toán
        db.delete("`Order`", "id=?", new String[]{String.valueOf(orderId)});           // Xoá hoá đơn
    }

}
