package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class dataCash extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    TinyDB tinydb;
    Gson gson;


    ArrayList<String> fields;
    ArrayList<String> act_types;
    ArrayList<String> act_and_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_cash);



        Button cashchat = findViewById(R.id.chat);
        ListView showdata = findViewById(R.id.showdata);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Operation");
        fields = new ArrayList<String>();


        act_types = new ArrayList<String>();
        act_and_type = new ArrayList<String>();
        cashchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        mDatabaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot name : task.getResult().getChildren()){
                            String fname = name.getKey();
                            fields.add(fname);
                        }
                        ArrayAdapter<String> fieledAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, fields);
                        showdata.setAdapter(fieledAdapter);
                    }
                }else{
                    Toast.makeText(dataCash.this, "Sorry bro!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

            }
        });



    }
}