package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private SQLiteDatabase db;

    public ProductDao(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Lấy toàn bộ sản phẩm
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Product", null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            int cateId = c.getInt(1);
            String name = c.getString(2);
            String desc = c.getString(3);
            double price = c.getDouble(4);
            String img = c.getString(5);
            list.add(new Product(id, cateId, name, desc, price, img));
        }
        c.close();
        return list;
    }

    // Thêm sản phẩm mới
    public void insert(Product p) {
        ContentValues values = new ContentValues();
        values.put("cateid", p.getCateId());
        values.put("name", p.getName());
        values.put("description", p.getDescription());
        values.put("price", p.getPrice());
        values.put("imagepath", p.getImagePath());
        db.insert("Product", null, values);
    }

    // Cập nhật sản phẩm
    public void update(Product p) {
        ContentValues values = new ContentValues();
        values.put("cateid", p.getCateId());
        values.put("name", p.getName());
        values.put("description", p.getDescription());
        values.put("price", p.getPrice());
        values.put("imagepath", p.getImagePath());
        db.update("Product", values, "id=?", new String[]{String.valueOf(p.getId())});
    }

    // Xóa sản phẩm theo id
    public void delete(int id) {
        db.delete("Product", "id=?", new String[]{String.valueOf(id)});
    }

    // ✅ Lấy sản phẩm theo ID
    public Product getById(int id) {
        Cursor c = db.rawQuery("SELECT * FROM Product WHERE id=?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            int cateId = c.getInt(c.getColumnIndexOrThrow("cateid"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            String desc = c.getString(c.getColumnIndexOrThrow("description"));
            double price = c.getDouble(c.getColumnIndexOrThrow("price"));
            String img = c.getString(c.getColumnIndexOrThrow("imagepath"));
            c.close();
            return new Product(id, cateId, name, desc, price, img);
        }
        c.close();
        return null;
    }

    // ✅ Tìm sản phẩm theo tên (trả về danh sách)
    public List<Product> getByName(String keyword) {
        List<Product> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Product WHERE name LIKE ?", new String[]{"%" + keyword + "%"});
        while (c.moveToNext()) {
            int id = c.getInt(0);
            int cateId = c.getInt(1);
            String name = c.getString(2);
            String desc = c.getString(3);
            double price = c.getDouble(4);
            String img = c.getString(5);
            list.add(new Product(id, cateId, name, desc, price, img));
        }
        c.close();
        return list;
    }
    // ✅ Lấy sản phẩm theo danh mục (cateId)
    public List<Product> getByCategory(int cateId) {
        List<Product> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Product WHERE cateid = ?", new String[]{String.valueOf(cateId)});
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(2);
            String desc = c.getString(3);
            double price = c.getDouble(4);
            String imagePath = c.getString(5);
            list.add(new Product(id, cateId, name, desc, price, imagePath));
        }
        c.close();
        return list;
    }



}
