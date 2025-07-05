package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Order;

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
}
