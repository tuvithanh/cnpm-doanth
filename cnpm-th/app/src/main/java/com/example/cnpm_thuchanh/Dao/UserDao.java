package com.example.cnpm_thuchanh.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.User;

public class UserDao {
    private SQLiteDatabase db;

    public UserDao(Context context) {
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public boolean register(User user) {
        if (checkUserExists(user.getUsername())) return false;

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("phone", 0);
        values.put("address", "");
        values.put("about", "");
        values.put("role", "U");
        values.put("favorites", "");

        long res = db.insert("User", null, values);
        return res != -1;
    }

    public boolean checkUserExists(String username) {
        Cursor c = db.rawQuery("SELECT * FROM User WHERE username=?", new String[]{username});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public boolean login(String username, String password) {
        Cursor c = db.rawQuery("SELECT * FROM User WHERE username=? AND password=?", new String[]{username, password});
        boolean success = c.getCount() > 0;
        c.close();
        return success;
    }
}
