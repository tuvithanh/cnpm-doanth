package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Cart;

public class CartDao {
    private DatabaseHelper dbHelper;

    public CartDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // ✅ Tạo hoặc lấy giỏ hàng hiện tại cho user
    public Cart getOrCreateCart(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Tìm cart hiện có
        Cursor cursor = db.rawQuery("SELECT * FROM Cart WHERE user_id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"));
            cursor.close();
            return new Cart(id, userId, createdAt);
        }
        cursor.close();

        // Nếu chưa có thì tạo mới
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        long newId = db.insert("Cart", null, values);

        Cursor newCursor = db.rawQuery("SELECT * FROM Cart WHERE id = ?", new String[]{String.valueOf(newId)});
        if (newCursor.moveToFirst()) {
            int id = newCursor.getInt(newCursor.getColumnIndexOrThrow("id"));
            String createdAt = newCursor.getString(newCursor.getColumnIndexOrThrow("created_at"));
            newCursor.close();
            return new Cart(id, userId, createdAt);
        }
        newCursor.close();
        return null;
    }
}
