package com.example.cafemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread welcomeThread = new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    finish();

                }
            }
        };

        welcomeThread.start();
    }
}