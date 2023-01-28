package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewopbyDate extends AppCompatActivity {
    ArrayList<String> times;
    ListView timeslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewopby_date);


        Intent intent = getIntent();
        String fname =intent.getStringExtra("fNAME");

        String settext = "Activity times at " + fname;

        TextView fnametext = findViewById(R.id.namef);
        fnametext.setText(settext);

        timeslist = findViewById(R.id.timestab);


        FirebaseDatabase fDB = FirebaseDatabase.getInstance();
        DatabaseReference DBtimes = fDB.getReference("Operation").child(fname);

        times = new ArrayList<String>();

        DBtimes.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot act_time : task.getResult().getChildren()){
                            String ftime = act_time.getKey();
                            String entryTime =  enTime (ftime);
                            times.add(entryTime);
                        }
                        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, times);
                        timeslist.setAdapter(timesAdapter);
                        }
                }else{
                    Toast.makeText(ViewopbyDate.this, "Sorry bro!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String enTime (String entry){

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long ent = Long.parseLong(entry);
        Date d = new Date(ent);
        String s = f.format(d);

        return s;
    }
}