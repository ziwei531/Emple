package com.example.emple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Main Activity will be the login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
    }

    public void handleLogin(View v) {
        Button button = (Button) v;
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}