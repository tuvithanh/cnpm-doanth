package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private SQLiteDatabase db;

    public CategoryDao(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Category", null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            list.add(new Category(id, name));
        }
        c.close();
        return list;
    }

    public void insert(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());  // ✅ Không cần ID
        db.insert("Category", null, values);
    }

    public void update(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        db.update("Category", values, "id=?", new String[]{String.valueOf(category.getId())});
    }

    public void delete(int id) {
        db.delete("Category", "id=?", new String[]{String.valueOf(id)});
    }

}


