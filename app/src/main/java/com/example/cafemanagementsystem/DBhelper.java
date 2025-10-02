package com.example.cafemanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CMS";
    public static final int DB_VERSION = 1;
    public DBhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
//        insertItems();
    }

    public static String TABLE1 = "CREATE TABLE registration_master(Reg_Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Email TEXT, Contact_No TEXT, Password TEXT)";
    public static String TABLE2 = "CREATE TABLE products(Prd_Id INTEGER PRIMARY KEY AUTOINCREMENT, Prd_Name TEXT, Prd_Price TEXT, Prd_Photo INTEGER)";
    public static String TABLE3 = "CREATE TABLE cart(Crt_Id INTEGER PRIMARY KEY AUTOINCREMENT, Cust_Id INTEGER, Prd_Id INTEGER, Quantity TEXT, Total INTEGER, FOREIGN KEY(Cust_Id) REFERENCES registration_master(Reg_Id), FOREIGN KEY(Prd_Id) REFERENCES products(Prd_Id))";
    public static String TABLE4 = "CREATE TABLE orders(Order_id INTEGER PRIMARY KEY AUTOINCREMENT, Cust_Id INTEGER, Date TEXT, Total INTEGER, FOREIGN KEY(Cust_Id) REFERENCES registration_master)";
    public static String TABLE5 = "CREATE TABLE order_products(Op_Id INTEGER PRIMARY KEY AUTOINCREMENT, Ord_Id INTEGER, Prd_Id INTEGER, Quantity TEXT, FOREIGN KEY(Ord_Id) REFERENCES orders, FOREIGN KEY(Prd_Id) REFERENCES products)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE1);
        db.execSQL(TABLE2);
        db.execSQL(TABLE3);
        db.execSQL(TABLE4);
        db.execSQL(TABLE5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS registration_master");
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_products");
    }

    public void insertItems()
    {
        SQLiteDatabase db4 = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Prd_Name", "Croissants");
        cv.put("Prd_Price", "240");
        cv.put("Prd_Photo", R.drawable.croissants);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Chicken Wings");
        cv.put("Prd_Price", "370");
        cv.put("Prd_Photo", R.drawable.chicken_wings);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "White Mocha");
        cv.put("Prd_Price", "280");
        cv.put("Prd_Photo", R.drawable.white_chocolate_mocha);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Cappuccino");
        cv.put("Prd_Price", "240");
        cv.put("Prd_Photo", R.drawable.cappucino);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Avocado Toast");
        cv.put("Prd_Price", "500");
        cv.put("Prd_Photo", R.drawable.avocado_toast);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Lasagna");
        cv.put("Prd_Price", "560");
        cv.put("Prd_Photo", R.drawable.lasagna);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Lemon Ricotta");
        cv.put("Prd_Price", "560");
        cv.put("Prd_Photo", R.drawable.lemonricotta_pasta);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Alfredo Pasta");
        cv.put("Prd_Price", "500");
        cv.put("Prd_Photo", R.drawable.alfredo_pasta);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Hawaiian Pizza");
        cv.put("Prd_Price", "660");
        cv.put("Prd_Photo", R.drawable.hawaiian_pizza);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Pizza Carbonarra");
        cv.put("Prd_Price", "720");
        cv.put("Prd_Photo", R.drawable.pizza_corbonara);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Brownie");
        cv.put("Prd_Price", "250");
        cv.put("Prd_Photo", R.drawable.brownie);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Oreo Truffle");
        cv.put("Prd_Price", "300");
        cv.put("Prd_Photo", R.drawable.oreo_truffle);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Witches Brew");
        cv.put("Prd_Price", "170");
        cv.put("Prd_Photo", R.drawable.witches_brew);
        db4.insert("products", null, cv);

        cv.put("Prd_Name", "Sprakling Lemonade");
        cv.put("Prd_Price", "170");
        cv.put("Prd_Photo", R.drawable.sparkling_lamonade);
        db4.insert("products", null, cv);
    }
}