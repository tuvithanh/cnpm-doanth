package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

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
    // Lấy danh sách sản phẩm trong đơn hàng
    public List<OrderDetail> getDetailsByOrderId(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM OrderDetail WHERE order_id=?", new String[]{String.valueOf(orderId)});
        while (c.moveToNext()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(c.getInt(c.getColumnIndexOrThrow("order_id")));
            detail.setProductId(c.getInt(c.getColumnIndexOrThrow("product_id")));
            detail.setQuantity(c.getInt(c.getColumnIndexOrThrow("quantity")));
            detail.setPrice(c.getDouble(c.getColumnIndexOrThrow("price")));
            list.add(detail);
        }
        c.close();
        return list;
    }

}
