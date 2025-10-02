package com.example.cafemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.*;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Register extends AppCompatActivity {

    EditText name, email, contact, pass, rpass;
    Button submit;
    ArrayList<String> cemail = new ArrayList<>();
    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submit = findViewById(R.id.btnregister);
        name = findViewById(R.id.RegisterName);
        email = findViewById(R.id.RegisterEmail);
        contact = findViewById(R.id.RegisterContact);
        pass = findViewById(R.id.RegisterPassword);
        rpass = findViewById(R.id.RegisterRpassword);
        db = new DBhelper(this);
        btnclick();
    }

    public void btnclick()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iname="", iemail="", icontact="", ipass="", irpass="";

                iname = name.getText().toString();
                iemail = email.getText().toString();
                icontact = contact.getText().toString();
                ipass = pass.getText().toString();
                irpass = rpass.getText().toString();

                if(!iname.equals("") && !iemail.equals("") && !icontact.equals("") && !ipass.equals("") && !irpass.equals("")) {
                    if(ipass.equals(irpass))
                    {
                        if(ipass.length() >= 8)
                        {
                            if(icontact.substring(0,1).equals("7") || icontact.substring(0,1).equals("8") || icontact.substring(0,1).equals("9") || icontact.substring(0,1).equals("6"))
                            {
                                if(icontact.length() == 10)
                                {
                                    String regex = "^(.+)@(.+)$";
                                    Pattern pattern = Pattern.compile(regex);
                                    Matcher matcher = pattern.matcher((CharSequence)iemail);
                                    if(matcher.matches())
                                    {
                                        SQLiteDatabase db1 = db.getReadableDatabase();
                                        Cursor c = db1.rawQuery("SELECT EMAIL FROM registration_master",null);
                                        while(c.moveToNext())
                                        {
                                            cemail.add(c.getString(0));
                                        }

                                        if(!cemail.contains(iemail))
                                        {
                                            SQLiteDatabase db2 = db.getWritableDatabase();
                                            ContentValues cv = new ContentValues();
                                            cv.put("Name", iname);
                                            cv.put("Email", iemail);
                                            cv.put("Contact_No", icontact);
                                            cv.put("Password", ipass);
                                            long result = db2.insert("registration_master", null, cv);

                                            if(result > 0)
                                            {
                                                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                                i.putExtra("UserId", iemail);
                                                startActivity(i);
                                            }
                                            else
                                            {
                                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(Register.this, "Account exists from this Email Id", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this, "Enter valid Emial Id", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Register.this, "Contact number must be equals to 10 characters", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Register.this, "Enter valid contact number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Password should be greater than 8 characters", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Register.this, "Password dosen't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Enter all the details properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}