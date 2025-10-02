package com.example.cafemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    ListView FoodDisplay;
    DBhelper db;
    BottomNavigationView nav;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<Integer> imgs = new ArrayList<>();
    ArrayList<Integer> PrdId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FoodDisplay = findViewById(R.id.FoodDisplay);
        nav = findViewById(R.id.NavView);
        db = new DBhelper(this);
        getdata();

        Custom_Dashboard_View adapter = new Custom_Dashboard_View(this, names, prices, imgs, PrdId);
        FoodDisplay.setAdapter(adapter);

        nav.setOnItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.home:
                    break;

                case R.id.cart:
                    cart();
                    break;
                case R.id.logout:
                    logout();
                    break;
            }

            return true;
        });
    }

    public void getdata()
    {
        SQLiteDatabase db5 = db.getReadableDatabase();
        Cursor c = db5.rawQuery("select * from products", null);

        while(c.moveToNext())
        {
            names.add(c.getString(1));
            prices.add(c.getString(2));
            imgs.add(c.getInt(3));
            PrdId.add(c.getInt(0));
        }
    }

    public void logout()
    {
        SharedPreferences sp = getSharedPreferences("Login",MODE_PRIVATE);
        sp.edit().clear().apply();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void cart()
    {
        startActivity(new Intent(getApplicationContext(), cart.class));
    }
}