package com.example.cafemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ListIterator;

public class cart extends AppCompatActivity {

    BottomNavigationView nav;
    TextView total;
    ListView CartView;
    Button chkout;
    DBhelper db;
    int gtotal = 0;
    int cid=0;
    String date = "";
    int Ord = 0;
    boolean x;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<Integer> total1 = new ArrayList<>();
    ArrayList<Integer> Imgs = new ArrayList<>();
    ArrayList<Integer> Cart_id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        nav = findViewById(R.id.CartNavView);
        total = findViewById(R.id.TotalView);
        CartView = findViewById(R.id.CartDisplay);
        chkout = findViewById(R.id.CheckOut);
        db = new DBhelper(this);
        total.setText("Total: 0.0");
        nav.getMenu().findItem(R.id.ccart).setChecked(true);
        getdata();
        navClick();
        getTotal();
        Cart_View adapter = new Cart_View(this, Imgs, name, quantity, total1, Cart_id);
        CartView.setAdapter(adapter);
        btnClick();
    }

    public void logout()
    {
        SharedPreferences sp = getSharedPreferences("Login",MODE_PRIVATE);
        sp.edit().clear().apply();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void getdata()
    {
        SharedPreferences sp2 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String cemail = sp2.getString("UserId", "");

        if(!cemail.equals(""))
        {
            SQLiteDatabase db6 = db.getReadableDatabase();
            Cursor c = db6.rawQuery("select Reg_Id from registration_master where Email='"+cemail+"'", null);
            if(c.moveToNext())
            {
                cid = c.getInt(0);
            }
        }

        if(cid!=0)
        {
            SQLiteDatabase db7 = db.getReadableDatabase();
            Cursor c = db7.rawQuery("select products.Prd_Name, cart.Quantity, cart.Total, products.Prd_Photo, cart.Crt_Id from cart inner join products on cart.Prd_id=products.Prd_Id where cart.Cust_Id='"+cid+"'", null);

            while(c.moveToNext()) {
                name.add(c.getString(0));
                quantity.add(c.getString(1));
                total1.add(c.getInt(2));
                Imgs.add(c.getInt(3));
                Cart_id.add(c.getInt(4));
            }
        }
        else
        {
            Toast.makeText(this, "Somethings wrong!!please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    public void navClick()
    {
        nav.setOnItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.chome:
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    break;

                case R.id.clogout:
                    logout();
                    break;
            }

            return true;
        });
    }

    public void getTotal()
    {
        SQLiteDatabase db8 = db.getReadableDatabase();
        Cursor c = db8.rawQuery("select sum(Total) from cart where Cust_Id='"+cid+"'", null);
        if(c.moveToNext())
        {
            gtotal = c.getInt(0);
            total.setText("Total: "+c.getInt(0));
        }
    }

    public void btnClick()
    {
        chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> Cust_Id = new ArrayList<>();
                ArrayList<Integer> Prd_Id = new ArrayList<>();
                ArrayList<String> Quan = new ArrayList<>();

                SQLiteDatabase db9 = db.getReadableDatabase();
                Cursor c = db9.rawQuery("select * from cart where Cust_Id='"+cid+"'", null);
                while(c.moveToNext())
                {
                    Prd_Id.add(c.getInt(2));
                    Quan.add(c.getString(3));
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    date = dtf.format(now);
                }

                if(!date.equals("")) {
                    SQLiteDatabase db10 = db.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("Cust_Id", cid);
                    cv.put("Date", date);
                    cv.put("Total", gtotal);
                    long insert = db10.insert("orders", null, cv);

                    if(insert > 0)
                    {
                        SQLiteDatabase db11 = db.getReadableDatabase();
                        Cursor c1 = db11.rawQuery("SELECT MAX(Order_id) FROM orders", null);
                        if(c1.moveToNext())
                        {
                            Ord = c1.getInt(0);
                        }

                        if(Ord != 0)
                        {
                            SQLiteDatabase db12 = db.getWritableDatabase();
                            ContentValues cv1 = new ContentValues();
                            ListIterator<Integer> it1 = Prd_Id.listIterator();
                            ListIterator<String> it2 = Quan.listIterator();
                            while(it1.hasNext() && it2.hasNext())
                            {
                                cv1.put("Ord_Id", Ord);
                                cv1.put("Prd_Id", it1.next());
                                cv1.put("Quantity", it2.next());

                                long result = db11.insert("order_products", null, cv1);
                                if(result > 0)
                                {
                                    x = true;
                                }
                                else
                                {
                                    x = false;
                                }
                            }

                            if(x)
                            {
                                SQLiteDatabase db13 = db.getWritableDatabase();
                                db13.execSQL("delete from cart where Cust_Id='"+cid+"'");
                                    Toast.makeText(cart.this, "Order placed", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            }
                        }
                        else
                        {
                            Toast.makeText(cart.this, "somethings wrong!!Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(cart.this, "order placing failed!!Please try again later", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(cart.this, "somethings wrong!!Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}