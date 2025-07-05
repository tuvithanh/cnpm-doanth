package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.OrderDetail;

public class OrderDetailDao {
    private final SQLiteDatabase db;

    public OrderDetailDao(Context context) {
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public void insertOrderDetail(int orderId, int productId, int quantity, double price) {
        ContentValues values = new ContentValues();
        values.put("order_id", orderId);
        values.put("product_id", productId);
        values.put("quantity", quantity);
        values.put("price", price);
        db.insert("OrderDetail", null, values);
    }

}
