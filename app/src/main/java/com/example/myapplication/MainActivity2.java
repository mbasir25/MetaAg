package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button user_profile, insert_field, insert_operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        insert_field = findViewById(R.id.insert_field);
        insert_operation = findViewById(R.id.insert_operation);
        user_profile = findViewById(R.id.user_profile);

        insert_field.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),drawpoly.class)));

        user_profile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), registration.class)));

        insert_operation.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), backg_gps.class)));


//
    }
}