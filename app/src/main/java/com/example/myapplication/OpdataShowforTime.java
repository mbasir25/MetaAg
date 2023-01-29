package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OpdataShowforTime extends AppCompatActivity {
    opAdapter opadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opdata_showfor_time);

        Intent intent = getIntent();
        String fname =intent.getStringExtra("fNAME");
        String fTime = intent.getStringExtra("lTIME");

        String toText = "Activity details for "+fname + " at " + fTime;

        TextView texts = findViewById(R.id.fieldTime);
        texts.setText(toText);

        RecyclerView recOp = findViewById(R.id.recOp);
        LinearLayoutManager recOpManager = new LinearLayoutManager(OpdataShowforTime.this);
        recOp.setLayoutManager(recOpManager);


        DatabaseReference ops = FirebaseDatabase.getInstance().getReference().child("Operation").child(fname).child(fTime);
        FirebaseRecyclerOptions<OpDataBind> op =
                new FirebaseRecyclerOptions.Builder<OpDataBind>()
                        .setQuery(ops, OpDataBind.class)
                        .build();

        opadapter =new opAdapter(op);
        recOp.setAdapter(opadapter);
        opadapter.startListening();


    }



}