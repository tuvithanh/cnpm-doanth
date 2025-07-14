package com.example.cnpm_thuchanh.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shop.db";
    private static final int DB_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng User
        db.execSQL("CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone INTEGER NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "about TEXT NOT NULL," +
                "role TEXT NOT NULL," +
                "favorites TEXT)");

        // Bảng Category
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)");

        // Bảng Product
        db.execSQL("CREATE TABLE Product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cateid INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "imagepath TEXT," +
                "sold_quantity INTEGER DEFAULT 0," +
                "FOREIGN KEY (cateid) REFERENCES Category(id))");

        // Bảng Cart
        db.execSQL("CREATE TABLE Cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP)");

        // Bảng CartItem
        db.execSQL("CREATE TABLE CartItem (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cart_id INTEGER," +
                "product_id INTEGER," +
                "quantity INTEGER," +
                "FOREIGN KEY (cart_id) REFERENCES Cart(id)," +
                "FOREIGN KEY (product_id) REFERENCES Product(id))");

        // Bảng Order
        db.execSQL("CREATE TABLE `Order` (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP," +
                "status TEXT DEFAULT 'Chờ xử lý'," +
                "total REAL NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id))");

        // Bảng OrderDetail
        db.execSQL("CREATE TABLE OrderDetail (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "price REAL NOT NULL," +
                "FOREIGN KEY (order_id) REFERENCES `Order`(id)," +
                "FOREIGN KEY (product_id) REFERENCES Product(id))");

        // Bảng Payment
        db.execSQL("CREATE TABLE Payment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "payment_method TEXT," +
                "paid_at TEXT DEFAULT CURRENT_TIMESTAMP," +
                "amount REAL NOT NULL," +
                "status TEXT DEFAULT 'Đã thanh toán'," +
                "FOREIGN KEY (order_id) REFERENCES `Order`(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        if (oldVer < 3) {
            db.execSQL("ALTER TABLE Product ADD COLUMN sold_quantity INTEGER DEFAULT 0");
        }
    }
}


