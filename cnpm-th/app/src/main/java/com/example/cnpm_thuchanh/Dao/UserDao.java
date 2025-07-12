package com.example.cnpm_thuchanh.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.User;

import java.util.ArrayList;
import java.util.List;

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
    public void createAdminIfNotExist() {
        // Không cần tạo lại db, dùng luôn biến db đã khai báo
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username = ?", new String[]{"admin"});
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("name", "Admin");
            values.put("username", "admin");
            values.put("password", "admin123");
            values.put("email", "admin@example.com");
            values.put("phone", 0);
            values.put("address", "Admin Address");
            values.put("about", "This is the admin account");
            values.put("role", "A"); // "A" for Admin
            values.put("favorites", "");

            db.insert("User", null, values);
        }
        cursor.close();
    }
    public User getUserByUsername(String username) {
        Cursor c = db.rawQuery("SELECT * FROM User WHERE username = ?", new String[]{username});
        if (c.moveToFirst()) {
            User user = new User();
            user.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            user.setName(c.getString(c.getColumnIndexOrThrow("name")));
            user.setUsername(c.getString(c.getColumnIndexOrThrow("username")));
            user.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
            user.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
            user.setPhone(c.getInt(c.getColumnIndexOrThrow("phone")));
            user.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
            user.setAbout(c.getString(c.getColumnIndexOrThrow("about")));
            user.setRole(c.getString(c.getColumnIndexOrThrow("role")));
            c.close();
            return user;
        }
        c.close();
        return null;
    }
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM User", null);
        while (c.moveToNext()) {
            User u = new User();
            u.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            u.setUsername(c.getString(c.getColumnIndexOrThrow("username")));
            u.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
            u.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
            u.setName(c.getString(c.getColumnIndexOrThrow("name")));
            list.add(u);
        }
        c.close();
        return list;
    }

    public void delete(int id) {
        db.delete("User", "id=?", new String[]{String.valueOf(id)});
    }
    public boolean update(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("address", user.getAddress());

        int result = db.update("User", values, "username = ?", new String[]{user.getUsername()});
        return result > 0;
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM User", null);
        while (c.moveToNext()) {
            User user = new User();
            user.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            user.setName(c.getString(c.getColumnIndexOrThrow("name")));
            user.setUsername(c.getString(c.getColumnIndexOrThrow("username")));
            user.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
            user.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
            user.setPhone(c.getInt(c.getColumnIndexOrThrow("phone")));
            user.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
            user.setAbout(c.getString(c.getColumnIndexOrThrow("about")));
            user.setRole(c.getString(c.getColumnIndexOrThrow("role")));
            user.setFavorites(c.getString(c.getColumnIndexOrThrow("favorites")));
            list.add(user);
        }
        c.close();
        return list;
    }






}
