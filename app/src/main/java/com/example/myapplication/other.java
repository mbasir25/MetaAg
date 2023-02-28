package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class other extends AppCompatActivity {

    boolean valid = true;

    ListView powlist, impllist, actlist,  matlist, crplist;
    ArrayList<String> activitylist, pnames, implementlist, materiallist,croplist;
    String stime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        LinearLayout act_lay = findViewById(R.id.act_lay);
        LinearLayout pow_lay = findViewById(R.id.pow_lay);
        LinearLayout imp_lay = findViewById(R.id.imp_lay);
        LinearLayout mat_lay = findViewById(R.id.mat_lay);
        LinearLayout crp_lay = findViewById(R.id.crp_lay);

        EditText actname = findViewById(R.id.act_name);
        EditText actsp = findViewById(R.id.act_sp);
        EditText powername = findViewById(R.id.pow_name);
        EditText powersp = findViewById(R.id.pow_sp);
        EditText implname = findViewById(R.id.impl_name);
        EditText matname = findViewById(R.id.mat_name);
        EditText matsp = findViewById(R.id.mat_sp);
        EditText crpname = findViewById(R.id.crp_name);

        Button act= findViewById(R.id.act);

        Button power= findViewById(R.id.power);

        Button add_act = findViewById(R.id.act_ad);

        Button add_powerU = findViewById(R.id.asset_ad);

        Button implem= findViewById(R.id.implem);

        Button add_impls = findViewById(R.id.impl_ad);


        Button mat= findViewById(R.id.mat);

        Button add_mat = findViewById(R.id.mat_ad);

        Button crp= findViewById(R.id.crp);
        Button add_crp = findViewById(R.id.crp_ad);

        actlist = findViewById(R.id.actlist);
        powlist = findViewById(R.id.powlist);
        impllist = findViewById(R.id.impllist);
        matlist = findViewById(R.id.matlist);
        crplist = findViewById(R.id.crplist);



        FirebaseDatabase field = FirebaseDatabase.getInstance();
        DatabaseReference activity = field.getReference("Activities");
        DatabaseReference asset = field.getReference("Assets");
        DatabaseReference impl = field.getReference("Implements");
        DatabaseReference mats = field.getReference("Materials");
        DatabaseReference crop = field.getReference("crop");
      /// Activity add

        activitylist = new ArrayList<String>();

        activity.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot nms : task.getResult().getChildren()){
                            String fnames = nms.getKey();

                            activitylist.add(fnames);
                        }
                        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, activitylist);
                        actlist.setAdapter(timesAdapter);

                        actlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                stime = timesAdapter.getItem(position);
                                if(act_lay.getVisibility() == View.GONE){
                                    act_lay.setVisibility(View.VISIBLE);
                                    actname.setText(stime);
                                    actlist.setVisibility(View.GONE);

                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(other.this, "failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actlist.getVisibility() == View.GONE){
                    actlist.setVisibility(View.VISIBLE);



                }else{
                    actlist.setVisibility(View.GONE);
                    act_lay.setVisibility(View.GONE);
                }

            }
        });

        add_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(actname);
                checkField(actsp);
                if(valid){
                    String acname= actname.getText().toString();
                    String specify= actsp.getText().toString();
                    bot_row_model qname = new bot_row_model(acname);
                    bot_row_model psp = new bot_row_model(specify);

                    boolean exist = false;

                    for (String name:activitylist){
                        if (acname.equals(name)) {
                            activity.child(name).child("type").push().setValue(psp);
                            exist = true;
                            break;

                        }
                    }
                    if (exist==false){
                        activity.child(acname).setValue(qname);
                        activity.child(acname).child("type").child(specify).setValue(psp);

                    }

                    actname.setText("");
                    actsp.setText("");

                }else {
                    Toast.makeText(other.this, "invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });





///   power units add
        pnames = new ArrayList<String>();

        asset.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot nms : task.getResult().getChildren()){
                            String fnames = nms.getKey();

                            pnames.add(fnames);
                        }
                        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, pnames);
                        powlist.setAdapter(timesAdapter);

                        powlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                stime = timesAdapter.getItem(position);
                                if(pow_lay.getVisibility() == View.GONE){
                                    pow_lay.setVisibility(View.VISIBLE);
                                    powername.setText(stime);
                                    powlist.setVisibility(View.GONE);

                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(other.this, "failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(powlist.getVisibility() == View.GONE){
                    powlist.setVisibility(View.VISIBLE);



                }else{
                    powlist.setVisibility(View.GONE);
                    pow_lay.setVisibility(View.GONE);
                }

            }
        });

        add_powerU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(powername);
                checkField(powersp);
                if(valid){
                    String powname= powername.getText().toString();
                    String specify= powersp.getText().toString();
                    bot_row_model qname = new bot_row_model(powname);
                    bot_row_model psp = new bot_row_model(specify);

                    boolean exist = false;

                    for (String name:materiallist){
                        if (powname.equals(name)) {
                            asset.child(name).child("specify").push().setValue(psp);
                            exist = true;
                            break;

                        }
                    }
                    if (exist==false){
                        asset.child(powname).setValue(qname);
                        asset.child(powname).child("specify").child(specify).setValue(psp);

                    }
                    powername.setText("");
                    powersp.setText("");

                }else {
                    Toast.makeText(other.this, "invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Implement setting
        implementlist = new ArrayList<String>();

        impl.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot nms : task.getResult().getChildren()){
                            String fnames = nms.getKey();

                            implementlist.add(fnames);
                        }
                        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, implementlist);
                        impllist.setAdapter(timesAdapter);

                        impllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                stime = timesAdapter.getItem(position);
                                if(imp_lay.getVisibility() == View.GONE){
                                    imp_lay.setVisibility(View.VISIBLE);
                                    implname.setText(stime);
                                    impllist.setVisibility(View.GONE);

                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(other.this, "failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        implem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(impllist.getVisibility() == View.GONE){
                    impllist.setVisibility(View.VISIBLE);

                }else{
                    impllist.setVisibility(View.GONE);
                    imp_lay.setVisibility(View.GONE);
                }

            }
        });




        add_impls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(implname);

                if(valid){
                    String powname= implname.getText().toString();

                    bot_row_model qname = new bot_row_model(powname);

                    impl.child(powname).setValue(qname);

                    implname.setText("");

                }

            }
        });
//  Material add



        materiallist = new ArrayList<String>();

        mats.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot nms : task.getResult().getChildren()){
                            String fnames = nms.getKey();

                            materiallist.add(fnames);
                        }
                        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, materiallist);
                        matlist.setAdapter(timesAdapter);

                        matlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                stime = timesAdapter.getItem(position);
                                if(mat_lay.getVisibility() == View.GONE){
                                    mat_lay.setVisibility(View.VISIBLE);
                                    matname.setText(stime);
                                    matlist.setVisibility(View.GONE);

                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(other.this, "failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(matlist.getVisibility() == View.GONE){
                    matlist.setVisibility(View.VISIBLE);



                }else{
                    matlist.setVisibility(View.GONE);
                    mat_lay.setVisibility(View.GONE);
                }

            }
        });

        add_mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(matname);
                checkField(matsp);
                if(valid){
                    String powname= matname.getText().toString();
                    String specify= matsp.getText().toString();
                    bot_row_model qname = new bot_row_model(powname);
                    bot_row_model psp = new bot_row_model(specify);
                    boolean exist = false;

                    for (String name:materiallist){
                        if (powname.equals(name)) {
                            mats.child(name).child("specify").push().setValue(psp);
                            exist = true;
                            break;

                        }
                    }
                    if (exist==false){
                        mats.child(powname).setValue(qname);
                        mats.child(powname).child("specify").child(specify).setValue(psp);
                    }
                    matname.setText("");
                    matsp.setText("");
                }else {
                    Toast.makeText(other.this, "invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });
    // Crop add

        croplist = new ArrayList<String>();

        crop.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
//                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot nms : task.getResult().getChildren()){
                            String fnames = nms.getKey();

                            croplist.add(fnames);
                        }
                        ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, croplist);
                        crplist.setAdapter(timesAdapter);

                        crplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                stime = timesAdapter.getItem(position);
                                if(crp_lay.getVisibility() == View.GONE){
                                    crp_lay.setVisibility(View.VISIBLE);
                                    crpname.setText(stime);
                                    crplist.setVisibility(View.GONE);

                                }
                            }
                        });

                    }
                }else{
                    Toast.makeText(other.this, "failed!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        crp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crplist.getVisibility() == View.GONE){
                    crplist.setVisibility(View.VISIBLE);

                }else{
                    crplist.setVisibility(View.GONE);
                    crp_lay.setVisibility(View.GONE);
                }

            }
        });




        add_crp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(crpname);

                if(valid){
                    String powname= crpname.getText().toString();

                    bot_row_model qname = new bot_row_model(powname);

                    crop.child(powname).setValue(qname);
                    crpname.setText("");
                }


            }
        });


    }







    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }
        else {
            valid = true;

        }
        return valid;
    }


}