package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class data_wizard extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    contentAdapter activityAdapter;
    contentAdapter activityTypeAdapter, assetAdapter;
    TinyDB tinydb;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_wizard);

        RecyclerView rec_act = findViewById(R.id.rec_act);
        RecyclerView rec_act_type = findViewById(R.id.rec_act_type);
        RecyclerView rec_asset = findViewById(R.id.rec_asset);
        RecyclerView rec_asset_type = findViewById(R.id.rec_asset_type);
        RecyclerView rec_implement = findViewById(R.id.rec_implement);
        RecyclerView rec_material = findViewById(R.id.rec_material);

        //define manager for each recview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rec_act.setLayoutManager(linearLayoutManager);
        LinearLayoutManager rec_act_type_Manager = new LinearLayoutManager(this);
        rec_act_type.setLayoutManager(rec_act_type_Manager);
        LinearLayoutManager rec_asset_Manager = new LinearLayoutManager(this);
        rec_asset.setLayoutManager(rec_asset_Manager);
//        rec_asset_type.setLayoutManager(linearLayoutManager);
//        rec_implement.setLayoutManager(linearLayoutManager);
//        rec_implement.setLayoutManager(linearLayoutManager);
//        rec_material.setLayoutManager(linearLayoutManager);


        //Invisible Layouts

        LinearLayout sec = findViewById(R.id.sec_l);
        LinearLayout sec2 = findViewById(R.id.sec2_l);
        LinearLayout third = findViewById(R.id.thrd_l);
        LinearLayout third2 = findViewById(R.id.thrd2_l);
        LinearLayout forth = findViewById(R.id.forth_l);
        LinearLayout forth2 = findViewById(R.id.forth2_l);
        LinearLayout fifth = findViewById(R.id.fifth_l);
        LinearLayout fifth2 = findViewById(R.id.fifth2_l);
        LinearLayout six = findViewById(R.id.six_l);
        LinearLayout six2 = findViewById(R.id.six2_l);
        LinearLayout sev = findViewById(R.id.sev_l);
        LinearLayout sev2 = findViewById(R.id.sev2_l);
        LinearLayout eig = findViewById(R.id.eig_l);
        LinearLayout eig2 = findViewById(R.id.eig2_l);
        LinearLayout nin = findViewById(R.id.nin_l);
        LinearLayout nin2 = findViewById(R.id.nin2_l);
        LinearLayout ten = findViewById(R.id.ten_l);


        TextView typetext = findViewById(R.id.secquestion);
        TextView get_name = findViewById(R.id.get_name);
        TextView act = findViewById(R.id.act_name);
        TextView act_type = findViewById(R.id.act_type);
        TextView asset_use = findViewById(R.id.asset_use);
        TextView asset_desc = findViewById(R.id.asset_desc);
        TextView asset_note = findViewById(R.id.asset_note);
        TextView implement = findViewById(R.id.implement);
        TextView material = findViewById(R.id.material);
        TextView mat_amount = findViewById(R.id.mat_amount);
        TextView work_amount = findViewById(R.id.work_amount);

        Button yes = findViewById(R.id.act_yes);
        Button no = findViewById(R.id.act_no);
        ImageButton input = findViewById(R.id.inputbutton);

        EditText textinput = findViewById(R.id.input_text);

        tinydb = new TinyDB(getApplicationContext());
        String fieldname = tinydb.getString("fieldname");
        String en = tinydb.getString("entry");
        String ex = tinydb.getString("exit");
        String st = "Field " + fieldname + " , entry at " + en;

        get_name.setText(st);


        // TODO! make clear refrence of child key and value.

        DatabaseReference activity = FirebaseDatabase.getInstance().getReference().child("Activities");
        FirebaseRecyclerOptions<bot_row_model> options =
                new FirebaseRecyclerOptions.Builder<bot_row_model>()
                        .setQuery(activity, bot_row_model.class)
                        .build();


        activityAdapter = new contentAdapter(options);
        rec_act.setAdapter(activityAdapter);
        activityAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
            @Override
            public void onItemClick(bot_row_model botRowModel) {

                act.setText(botRowModel.getContent());
                sec.setVisibility(View.VISIBLE);
                sec2.setVisibility(View.VISIBLE);
                act.setVisibility(View.VISIBLE);
                typetext.setText("What type of "+ botRowModel.getContent() + " you performed?");
                rec_act_type.setVisibility(View.VISIBLE);

                s = botRowModel.getContent();

                DatabaseReference activity_type_ref = FirebaseDatabase.getInstance().getReference().child("Activities").child(s).child("type");
//                FirebaseRecyclerOptions<bot_row_model> types = setdata(activity_type_ref);
                FirebaseRecyclerOptions<bot_row_model> types =
                        new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                .setQuery(activity_type_ref, bot_row_model.class)
                                .build();

                activityTypeAdapter =new contentAdapter(types);

                rec_act_type.setAdapter(activityTypeAdapter);
                activityTypeAdapter.startListening();
                activityTypeAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                    @Override
                    public void onItemClick(bot_row_model botRowModel) {
                        third.setVisibility(View.VISIBLE);
                        third2.setVisibility(View.VISIBLE);
                        act_type.setVisibility(View.VISIBLE);
                        act_type.setText(botRowModel.getContent());
                        rec_asset.setVisibility(View.VISIBLE);
                        DatabaseReference assets = FirebaseDatabase.getInstance().getReference().child("Assets");
//                FirebaseRecyclerOptions<bot_row_model> types = setdata(activity_type_ref);
                        FirebaseRecyclerOptions<bot_row_model> asset =
                                new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                        .setQuery(assets, bot_row_model.class)
                                        .build();

                        assetAdapter =new contentAdapter(asset);
                        rec_asset.setAdapter(assetAdapter);
                        assetAdapter.startListening();

                    }
                });




            }

        });











    }

    @Override
    protected void onStart() {
        super.onStart();
        activityAdapter.startListening();
//
    }



    @Override
    protected void onStop() {
        super.onStop();
        activityAdapter.stopListening();
        activityTypeAdapter.stopListening();
    }


//    public FirebaseRecyclerOptions<bot_row_model> setdata(DatabaseReference ref) {
//        FirebaseRecyclerOptions<bot_row_model> options =
//        new FirebaseRecyclerOptions.Builder<bot_row_model>()
//                .setQuery(ref, bot_row_model.class)
//                .build();
//        return options;
//    }







}