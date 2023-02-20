package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button join;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join = findViewById(R.id.btn_join);
        tinyDB = new TinyDB(getApplicationContext());

        join.setOnClickListener((View view) -> {
            Map<String, ?> allEntries = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getAll();
            boolean exist= false;
            for (Map.Entry<String, ?> data_Entry : allEntries.entrySet()) {
//                    Log.i("workfields", data_Entry.getKey());
                if ("name".equals(data_Entry.getKey())) {
                    String name = tinyDB.getString("name");
                    if (name != null){
                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    }else{
                        startActivity(new Intent(getApplicationContext(), UserLogin.class));
                    }
                    exist = true;
                    break;
                }
            }
            if(exist == false){
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
//            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        });

    }
}


