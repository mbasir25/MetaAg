package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class dataCash extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    TinyDB tinydb;
    Gson gson;

    ArrayList<String> activities;
    ArrayList<String> act_types;
    ArrayList<String> act_and_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_cash);



        Button cashchat = findViewById(R.id.chat);
        ListView showdata = findViewById(R.id.showdata);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        activities = new ArrayList<String>();
        act_types = new ArrayList<String>();
        act_and_type = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,activities);
        initializeListView(adapter);

        cashchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdata.setAdapter(adapter);
            }
        });






    }

    private void initializeListView(ArrayAdapter<String> adapter) {

        gson = new Gson();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        // below line is used for getting reference
        // of our Firebase Database.


        // in below line we are calling method for add child event
        // listener to get the child of our database.
        mDatabaseReference.child("activities").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot act : snapshot.getChildren()){
                    String act_name = act.getKey();
                    activities.add(act_name);

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
                activities.remove(snapshot.getKey());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}