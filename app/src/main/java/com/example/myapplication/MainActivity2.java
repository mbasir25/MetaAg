package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button user_profile, insert_field, insert_operation, view_data, settings, insert_opt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        insert_field = findViewById(R.id.insert_field);
        insert_operation = findViewById(R.id.insert_operation);
        user_profile = findViewById(R.id.user_profile);
        view_data = findViewById(R.id.view_data);
        settings = findViewById(R.id.settings);
        insert_opt = findViewById(R.id.insert_opt);

        insert_field.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),drawpoly.class)));

        user_profile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), userDetail.class)));

        insert_operation.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), backg_gps.class)));
        view_data.setOnClickListener((View view) -> {
            startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), dataCash.class));
            }
        });
        insert_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), other.class));
            }
        });



//
    }
}