package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Payment;

public class PaymentDao {
    private final SQLiteDatabase db;

    public PaymentDao(Context context) {
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public long insertPayment(int orderId, String method, double amount) {
        ContentValues values = new ContentValues();
        values.put("order_id", orderId);
        values.put("payment_method", method);
        values.put("amount", amount);
        values.put("status", "Đã thanh toán");
        return db.insert("Payment", null, values);
    }

}
