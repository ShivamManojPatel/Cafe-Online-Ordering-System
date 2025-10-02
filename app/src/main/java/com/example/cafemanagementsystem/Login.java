package com.example.cafemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    TextView register;
    EditText Email, Pass;
    CheckBox remme;
    Button login;
    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);

        if(sp1.getBoolean("LoggedIn", false))
        {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        }
        register = findViewById(R.id.Register);
        Email = findViewById(R.id.TextId);
        Pass = findViewById(R.id.TextPassword);
        remme = findViewById(R.id.checkBox);
        login = findViewById(R.id.btnlogin);
        db = new DBhelper(this);
        btnClick();
    }

    public void btnClick()
    {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iemail = "", ipass = "";

                iemail = Email.getText().toString();
                ipass = Pass.getText().toString();

                if(!iemail.equals("") && !ipass.equals(""))
                {
                    String regex = "^(.+)@(.+)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher((CharSequence)iemail);
                    if(matcher.matches())
                    {
                        if(isValid(iemail, ipass))
                        {
                            if(remme.isChecked())
                            {
                                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("UserId", iemail);
                                editor.putBoolean("LoggedIn", true);
                                editor.apply();
                            }

                            Intent i = new Intent(getApplicationContext(), Dashboard.class);
                            i.putExtra("UserId", iemail);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Enter valid Email Id", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "Enter all the details properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValid(String email, String Pass)
    {
        boolean flag = false;

        SQLiteDatabase db3 = db.getReadableDatabase();
        Cursor c = db3.rawQuery("select Password from registration_master where Email=?", new String[]{email});

        if(c.moveToNext())
        {
            if(c.getString(0).equals(Pass))
            {
                flag = true;
            }
        }

        return flag;
    }
}