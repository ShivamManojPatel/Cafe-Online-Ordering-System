package com.example.cafemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Custom_Dashboard_View extends ArrayAdapter<String> {

    Activity context;
    TextView price, name;
    ImageView img;
    EditText quan;
    Button btncart;
    String text="1";
    DBhelper db;
    int x = 1;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<Integer> imgs = new ArrayList<>();
    ArrayList<Integer> PrdId = new ArrayList<>();
    public Custom_Dashboard_View(Activity context, ArrayList<String> names, ArrayList<String> prices, ArrayList<Integer> imgs, ArrayList<Integer> PrdId)
    {
        super(context, R.layout.activity_custom_dashboard_view, names);
        this.context = context;
        this.names = names;
        this.prices = prices;
        this.imgs = imgs;
        this.PrdId = PrdId;
        db = new DBhelper(context);
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.activity_custom_dashboard_view, null, true);

        price = view.findViewById(R.id.foodPrice);
        name = view.findViewById(R.id.FoodName);
        img = view.findViewById(R.id.FoodImage);
        btncart = view.findViewById(R.id.BtnCart);
        quan = view.findViewById(R.id.Quantity);

        quan.setText("1");
        quan.setTag(position);
        price.setText("Price: "+prices.get(position));
        name.setText(names.get(position));
        img.setImageResource(imgs.get(position));

        quan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
            }
        });

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(text);
                int pr = Integer.parseInt(prices.get(position));
                int tal = pr*val;
                //Toast.makeText(context, Integer.toString(pr*val), Toast.LENGTH_SHORT).show();
                int id = PrdId.get(position);
                int cid=0;
                //SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences sp2 = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
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

                if(cid!=0) {
                    SQLiteDatabase db7 = db.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("Cust_Id", cid);
                    cv.put("Prd_Id", id);
                    cv.put("Quantity", text);
                    cv.put("Total", tal);

                    long i = db7.insert("cart", null, cv);

                    if(i > 0)
                    {
                        Toast.makeText(context, "Inserted into cart", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Inserting into cart failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Please try again later", Toast.LENGTH_SHORT).show();
                }

                text="1";
            }
        });

        return view;
    }
}