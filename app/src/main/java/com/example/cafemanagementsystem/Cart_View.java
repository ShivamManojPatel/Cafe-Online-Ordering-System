package com.example.cafemanagementsystem;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart_View extends ArrayAdapter<String> {

    Activity context;
    ArrayList<Integer> Imgs = new ArrayList<>();
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<Integer> prices = new ArrayList<>();
    ArrayList<Integer> Cart_Id = new ArrayList<>();

    TextView price, quan, name;
    ImageView img;
    Button remove;
    DBhelper db;

    public Cart_View(Activity context, ArrayList<Integer> Imgs, ArrayList<String> Names, ArrayList<String> quantity, ArrayList<Integer> prices, ArrayList<Integer> Cart_Id) {
        super(context, R.layout.activity_cart_view, Names);

        this.context = context;
        this.Imgs = Imgs;
        this.Names = Names;
        this.quantity = quantity;
        this.prices = prices;
        this.Cart_Id = Cart_Id;
        db = new DBhelper(context);
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.activity_cart_view, null, true);

        price = view.findViewById(R.id.CartFoodTotal);
        quan = view.findViewById(R.id.CartFoodQuantity);
        name = view.findViewById(R.id.CartFoodName);
        img = view.findViewById(R.id.CartFoodImage);
        remove = view.findViewById(R.id.BtnRemove);

        price.setText("Total: " + prices.get(position));
        quan.setText("Quantity: " + quantity.get(position));
        name.setText(Names.get(position));
        img.setImageResource(Imgs.get(position));

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db8 = db.getWritableDatabase();
                long result = db8.delete("cart","Crt_Id=?", new String[]{Cart_Id.get(position).toString()});
                if(result > 0)
                {
                    prices.remove(position);
                    quantity.remove(position);
                    Names.remove(position);
                    Imgs.remove(position);
                    Cart_Id.remove(position);

                    Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, cart.class));
                }
                else
                {
                    Toast.makeText(context, "Item removal failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}