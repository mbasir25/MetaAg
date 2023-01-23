package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.StringValue;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class data_wizard extends AppCompatActivity {

    contentAdapter activityAdapter;
    contentAdapter activityTypeAdapter, cropAdapter, assetAdapter, assetTypeAdapter, implementAdapter, materialAdapter, materialSPAdapter;
    TinyDB tinydb;
    String s;

    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_wizard);

        RecyclerView rec_act = findViewById(R.id.rec_act);
        RecyclerView rec_act_type = findViewById(R.id.rec_act_type);
        RecyclerView rec_crops = findViewById(R.id.rec_crops);
        RecyclerView rec_asset = findViewById(R.id.rec_asset);
        RecyclerView rec_asset_type = findViewById(R.id.rec_asset_type);
        RecyclerView rec_implement = findViewById(R.id.rec_implement);
        RecyclerView rec_material = findViewById(R.id.rec_material);
        RecyclerView rec_material_SP = findViewById(R.id.rec_material_sp);


        //define manager for each recview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rec_act.setLayoutManager(linearLayoutManager);
        LinearLayoutManager rec_crops_Manager = new LinearLayoutManager(this);
        rec_crops.setLayoutManager(rec_crops_Manager);
        LinearLayoutManager rec_act_type_Manager = new LinearLayoutManager(this);
        rec_act_type.setLayoutManager(rec_act_type_Manager);
        LinearLayoutManager rec_asset_Manager = new LinearLayoutManager(this);
        rec_asset.setLayoutManager(rec_asset_Manager);
        LinearLayoutManager rec_asset_type_Manager = new LinearLayoutManager(this);
        rec_asset_type.setLayoutManager(rec_asset_type_Manager);
        LinearLayoutManager rec_implement_Manager = new LinearLayoutManager(this);
        rec_implement.setLayoutManager(rec_implement_Manager);
        LinearLayoutManager rec_material_Manager = new LinearLayoutManager(this);
        rec_material.setLayoutManager(rec_material_Manager);
        LinearLayoutManager rec_material_SP_Manager = new LinearLayoutManager(this);
        rec_material_SP.setLayoutManager(rec_material_SP_Manager);



        //Invisible Layouts
        LinearLayout cropl = findViewById(R.id.crop_l);
        LinearLayout crop2l = findViewById(R.id.crop2_l);
        LinearLayout cropratel = findViewById(R.id.croprate_l);
        LinearLayout croprate2l = findViewById(R.id.croprate2_l);
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
        LinearLayout mat_sp = findViewById(R.id.material_sp_l);
        LinearLayout mat2_sp = findViewById(R.id.material_sp2_l);
        LinearLayout eig = findViewById(R.id.eig_l);
        LinearLayout eig2 = findViewById(R.id.eig2_l);
        LinearLayout nin = findViewById(R.id.nin_l);
        LinearLayout nin2 = findViewById(R.id.nin2_l);
        LinearLayout ten = findViewById(R.id.ten_l);


        TextView typetext = findViewById(R.id.secquestion);
        TextView typeassettext = findViewById(R.id.forthquestion);
        TextView get_name = findViewById(R.id.get_name);
        TextView act = findViewById(R.id.act_name);
        TextView cropt = findViewById(R.id.crop);
        TextView cropr = findViewById(R.id.croprate);
        TextView act_type = findViewById(R.id.act_type);
        TextView asset_use = findViewById(R.id.asset_use);
        TextView asset_desc = findViewById(R.id.asset_desc);
        TextView asset_note = findViewById(R.id.asset_note);
        TextView implement = findViewById(R.id.implement);
        TextView material = findViewById(R.id.material);
        TextView material_SP = findViewById(R.id.material_sp);
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
                act.setVisibility(View.VISIBLE);
                s = botRowModel.getContent();

                //TODO crop question here !

                sec.setVisibility(View.VISIBLE);
                sec2.setVisibility(View.VISIBLE);

                typetext.setText("What type of "+ botRowModel.getContent() + " you performed?");
                rec_act_type.setVisibility(View.VISIBLE);



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

                        act_type.setVisibility(View.VISIBLE);
                        act_type.setText(botRowModel.getContent());

                        if (String.valueOf(act.getText()).equals("Sowing")){
                            cropl.setVisibility(View.VISIBLE);
                            crop2l.setVisibility(View.VISIBLE);
                            rec_crops.setVisibility(View.VISIBLE);
                            DatabaseReference crops = FirebaseDatabase.getInstance().getReference().child("crop");
                            FirebaseRecyclerOptions<bot_row_model> crop =
                                    new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                            .setQuery(crops, bot_row_model.class)
                                            .build();
                            cropAdapter =new contentAdapter(crop);
                            rec_crops.setAdapter(cropAdapter);
                            cropAdapter.startListening();
                            cropAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                @Override
                                public void onItemClick(bot_row_model botRowModel) {
                                    cropt.setVisibility(View.VISIBLE);
                                    cropt.setText(botRowModel.getContent());
                                    cropratel.setVisibility(View.VISIBLE);
                                    croprate2l.setVisibility(View.VISIBLE);
                                    input.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String rate = String.valueOf(textinput.getText());
                                            cropr.setVisibility(View.VISIBLE);
                                            cropr.setText(rate);
                                            third.setVisibility(View.VISIBLE);
                                            third2.setVisibility(View.VISIBLE);
                                            rec_asset.setVisibility(View.VISIBLE);
                                            String asset_name = botRowModel.getContent();
                                            DatabaseReference assets = FirebaseDatabase.getInstance().getReference().child("Assets");
//                FirebaseRecyclerOptions<bot_row_model> types = setdata(activity_type_ref);
                                            FirebaseRecyclerOptions<bot_row_model> asset =
                                                    new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                            .setQuery(assets, bot_row_model.class)
                                                            .build();

                                            assetAdapter =new contentAdapter(asset);
                                            rec_asset.setAdapter(assetAdapter);
                                            assetAdapter.startListening();
                                            assetAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                @Override
                                                public void onItemClick(bot_row_model botRowModel) {
                                                    asset_use.setVisibility(View.VISIBLE);
                                                    asset_use.setText(botRowModel.getContent());
                                                    forth.setVisibility(View.VISIBLE);
                                                    forth2.setVisibility(View.VISIBLE);
                                                    typeassettext.setText("Which "+ botRowModel.getContent() +" did you use?" );
                                                    rec_asset_type.setVisibility(View.VISIBLE);
                                                    String asset_type_S = botRowModel.getContent();
                                                    DatabaseReference assets_type = FirebaseDatabase.getInstance().getReference().child("Assets").child(asset_type_S).child("specify");
                                                    FirebaseRecyclerOptions<bot_row_model> asset_type =
                                                            new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                    .setQuery(assets_type, bot_row_model.class)
                                                                    .build();

                                                    assetTypeAdapter = new contentAdapter(asset_type);
                                                    rec_asset_type.setAdapter(assetTypeAdapter);
                                                    assetTypeAdapter.startListening();
                                                    assetTypeAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                        @Override
                                                        public void onItemClick(bot_row_model botRowModel) {
                                                            fifth.setVisibility(View.VISIBLE);
                                                            fifth2.setVisibility(View.VISIBLE);
                                                            asset_desc.setVisibility(View.VISIBLE);
                                                            asset_desc.setText(botRowModel.getContent());

                                                            input.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    String inp = String.valueOf(textinput.getText());
                                                                    if (inp != null){
                                                                        asset_note.setVisibility(View.VISIBLE);
                                                                        asset_note.setText(inp);
                                                                        textinput.setText("");
                                                                        six.setVisibility(View.VISIBLE);
                                                                        six2.setVisibility(View.VISIBLE);
                                                                        rec_implement.setVisibility(View.VISIBLE);

                                                                        DatabaseReference implement_ref = FirebaseDatabase.getInstance().getReference().child("Implements");
//                !TODO redesign the sequence depending on operations or machine used
                                                                        FirebaseRecyclerOptions<bot_row_model> impls =
                                                                                new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                                        .setQuery(implement_ref, bot_row_model.class)
                                                                                        .build();

                                                                        implementAdapter =new contentAdapter(impls);
                                                                        rec_implement.setAdapter(implementAdapter);
                                                                        implementAdapter.startListening();
                                                                        implementAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                                            @Override
                                                                            public void onItemClick(bot_row_model botRowModel) {
                                                                                sev.setVisibility(View.VISIBLE);
                                                                                sev2.setVisibility(View.VISIBLE);
                                                                                implement.setVisibility(View.VISIBLE);
                                                                                implement.setText(botRowModel.getContent());
                                                                                rec_material.setVisibility(View.VISIBLE);

                                                                                DatabaseReference materials_ref = FirebaseDatabase.getInstance().getReference().child("Materials");
                                                                                FirebaseRecyclerOptions<bot_row_model> mats =
                                                                                        new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                                                .setQuery(materials_ref, bot_row_model.class)
                                                                                                .build();
                                                                                materialAdapter =new contentAdapter(mats);
                                                                                rec_material.setAdapter(materialAdapter);
                                                                                materialAdapter.startListening();
                                                                                materialAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                                                    @Override
                                                                                    public void onItemClick(bot_row_model botRowModel) {
                                                                                        material.setVisibility(View.VISIBLE);
                                                                                        material.setText(botRowModel.getContent());
                                                                                        String matsp = botRowModel.getContent();
                                                                                        if (matsp.equals("No material used")){
                                                                                            String inp3 = String.valueOf(textinput.getText());
                                                                                            work_amount.setVisibility(View.VISIBLE);
                                                                                            work_amount.setText(inp3);

                                                                                            ten.setVisibility(View.VISIBLE);

                                                                                            no.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                            material, material_SP, mat_amount, work_amount);


                                                                                                    startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
                                                                                                }
                                                                                            });
                                                                                            yes.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                            material, material_SP, mat_amount, work_amount);
                                                                                                    recreate();

                                                                                                }
                                                                                            });

                                                                                        }else {
                                                                                            mat_sp.setVisibility(View.VISIBLE);
                                                                                            mat2_sp.setVisibility(View.VISIBLE);
                                                                                            rec_material_SP.setVisibility(View.VISIBLE);

                                                                                            DatabaseReference refs = FirebaseDatabase.getInstance().getReference();

                                                                                            DatabaseReference materials_SP_ref = refs.child("Materials").child(matsp).child("specify");

                                                                                            FirebaseRecyclerOptions<bot_row_model> mats_SP =
                                                                                                    new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                                                            .setQuery(materials_SP_ref, bot_row_model.class)
                                                                                                            .build();
                                                                                            materialSPAdapter = new contentAdapter(mats_SP);
                                                                                            rec_material_SP.setAdapter(materialSPAdapter);
                                                                                            materialSPAdapter.startListening();
                                                                                            materialSPAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                                                                @Override
                                                                                                public void onItemClick(bot_row_model botRowModel) {
                                                                                                    material_SP.setVisibility(View.VISIBLE);
                                                                                                    material_SP.setText(botRowModel.getContent());
                                                                                                    eig.setVisibility(View.VISIBLE);
                                                                                                    eig2.setVisibility(View.VISIBLE);
                                                                                                    input.setOnClickListener(new View.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(View v) {
                                                                                                            String inp1 = String.valueOf(textinput.getText());
                                                                                                            mat_amount.setVisibility(View.VISIBLE);
                                                                                                            mat_amount.setText(inp1);
                                                                                                            textinput.setText("");
                                                                                                            nin.setVisibility(View.VISIBLE);
                                                                                                            nin2.setVisibility(View.VISIBLE);
                                                                                                            input.setOnClickListener(new View.OnClickListener() {
                                                                                                                @Override
                                                                                                                public void onClick(View v) {
                                                                                                                    String inp2 = String.valueOf(textinput.getText());
                                                                                                                    work_amount.setVisibility(View.VISIBLE);
                                                                                                                    work_amount.setText(inp2);
                                                                                                                    textinput.setText("");
                                                                                                                    ten.setVisibility(View.VISIBLE);

                                                                                                                    no.setOnClickListener(new View.OnClickListener() {
                                                                                                                        @Override
                                                                                                                        public void onClick(View v) {
                                                                                                                            savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                                                    material, material_SP, mat_amount, work_amount);


                                                                                                                            startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
                                                                                                                        }
                                                                                                                    });
                                                                                                                    yes.setOnClickListener(new View.OnClickListener() {
                                                                                                                        @Override
                                                                                                                        public void onClick(View v) {
                                                                                                                            savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                                                    material, material_SP, mat_amount, work_amount);
                                                                                                                            recreate();

                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        });

                                                                    }

                                                                }
                                                            });

                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }else if (String.valueOf(act.getText()).equals("Irrigation") ) {
                            textinput.setText("");
                            nin.setVisibility(View.VISIBLE);
                            nin2.setVisibility(View.VISIBLE);
                            input.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String inp4 = String.valueOf(textinput.getText());
                                    work_amount.setVisibility(View.VISIBLE);
                                    work_amount.setText(inp4);
                                    textinput.setText("");
                                    ten.setVisibility(View.VISIBLE);

                                    no.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                    material, material_SP, mat_amount, work_amount);


                                            startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
                                        }
                                    });
                                    yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                    material, material_SP, mat_amount, work_amount);
                                            recreate();

                                        }
                                    });
                                }
                            });

                        }
                        else {

                            third.setVisibility(View.VISIBLE);
                            third2.setVisibility(View.VISIBLE);
                            rec_asset.setVisibility(View.VISIBLE);
                            String asset_name = botRowModel.getContent();
                            DatabaseReference assets = FirebaseDatabase.getInstance().getReference().child("Assets");
//                FirebaseRecyclerOptions<bot_row_model> types = setdata(activity_type_ref);
                            FirebaseRecyclerOptions<bot_row_model> asset =
                                    new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                            .setQuery(assets, bot_row_model.class)
                                            .build();

                            assetAdapter = new contentAdapter(asset);
                            rec_asset.setAdapter(assetAdapter);
                            assetAdapter.startListening();
                            assetAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                @Override
                                public void onItemClick(bot_row_model botRowModel) {
                                    asset_use.setVisibility(View.VISIBLE);
                                    asset_use.setText(botRowModel.getContent());
                                    forth.setVisibility(View.VISIBLE);
                                    forth2.setVisibility(View.VISIBLE);
                                    typeassettext.setText("Which "+ botRowModel.getContent() +" did you use?" );
                                    rec_asset_type.setVisibility(View.VISIBLE);
                                    String asset_type_S = botRowModel.getContent();
                                    DatabaseReference assets_type = FirebaseDatabase.getInstance().getReference().child("Assets").child(asset_type_S).child("specify");
                                    FirebaseRecyclerOptions<bot_row_model> asset_type =
                                            new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                    .setQuery(assets_type, bot_row_model.class)
                                                    .build();

                                    assetTypeAdapter = new contentAdapter(asset_type);
                                    rec_asset_type.setAdapter(assetTypeAdapter);
                                    assetTypeAdapter.startListening();
                                    assetTypeAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                        @Override
                                        public void onItemClick(bot_row_model botRowModel) {

                                            asset_desc.setVisibility(View.VISIBLE);
                                            asset_desc.setText(botRowModel.getContent());

                                            if (String.valueOf(act.getText()).equals("Scouting")){
                                                textinput.setText("");
                                                nin.setVisibility(View.VISIBLE);
                                                nin2.setVisibility(View.VISIBLE);
                                                input.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String inp4 = String.valueOf(textinput.getText());
                                                        work_amount.setVisibility(View.VISIBLE);
                                                        work_amount.setText(inp4);
                                                        textinput.setText("");
                                                        ten.setVisibility(View.VISIBLE);

                                                        no.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                        material, material_SP, mat_amount, work_amount);


                                                                startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
                                                            }
                                                        });
                                                        yes.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                        material, material_SP, mat_amount, work_amount);
                                                                recreate();

                                                            }
                                                        });
                                                    }
                                                });
                                            }else {

                                                fifth.setVisibility(View.VISIBLE);
                                                fifth2.setVisibility(View.VISIBLE);

                                                input.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String inp = String.valueOf(textinput.getText());
                                                        if (inp != null) {
                                                            asset_note.setVisibility(View.VISIBLE);
                                                            asset_note.setText(inp);
                                                            textinput.setText("");
                                                            six.setVisibility(View.VISIBLE);
                                                            six2.setVisibility(View.VISIBLE);
                                                            rec_implement.setVisibility(View.VISIBLE);

                                                            DatabaseReference implement_ref = FirebaseDatabase.getInstance().getReference().child("Implements");
//                !TODO redesign the sequence depending on operations or machine used
                                                            FirebaseRecyclerOptions<bot_row_model> impls =
                                                                    new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                            .setQuery(implement_ref, bot_row_model.class)
                                                                            .build();

                                                            implementAdapter = new contentAdapter(impls);
                                                            rec_implement.setAdapter(implementAdapter);
                                                            implementAdapter.startListening();
                                                            implementAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                                @Override
                                                                public void onItemClick(bot_row_model botRowModel) {
                                                                    sev.setVisibility(View.VISIBLE);
                                                                    sev2.setVisibility(View.VISIBLE);
                                                                    implement.setVisibility(View.VISIBLE);
                                                                    implement.setText(botRowModel.getContent());
                                                                    rec_material.setVisibility(View.VISIBLE);

                                                                    DatabaseReference materials_ref = FirebaseDatabase.getInstance().getReference().child("Materials");
                                                                    FirebaseRecyclerOptions<bot_row_model> mats =
                                                                            new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                                    .setQuery(materials_ref, bot_row_model.class)
                                                                                    .build();
                                                                    materialAdapter = new contentAdapter(mats);
                                                                    rec_material.setAdapter(materialAdapter);
                                                                    materialAdapter.startListening();
                                                                    materialAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                                        @Override
                                                                        public void onItemClick(bot_row_model botRowModel) {
                                                                            material.setVisibility(View.VISIBLE);
                                                                            material.setText(botRowModel.getContent());

                                                                            String matsp = botRowModel.getContent();
                                                                            if (matsp.equals("No material used")) {
                                                                                String inp3 = String.valueOf(textinput.getText());
                                                                                work_amount.setVisibility(View.VISIBLE);
                                                                                work_amount.setText(inp3);

                                                                                ten.setVisibility(View.VISIBLE);

                                                                                no.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                material, material_SP, mat_amount, work_amount);


                                                                                        startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
                                                                                    }
                                                                                });
                                                                                yes.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                material, material_SP, mat_amount, work_amount);
                                                                                        recreate();

                                                                                    }
                                                                                });

                                                                            } else {
                                                                                mat_sp.setVisibility(View.VISIBLE);
                                                                                mat2_sp.setVisibility(View.VISIBLE);
                                                                                rec_material_SP.setVisibility(View.VISIBLE);

                                                                                DatabaseReference refs = FirebaseDatabase.getInstance().getReference();

                                                                                DatabaseReference materials_SP_ref = refs.child("Materials").child(matsp).child("specify");

                                                                                FirebaseRecyclerOptions<bot_row_model> mats_SP =
                                                                                        new FirebaseRecyclerOptions.Builder<bot_row_model>()
                                                                                                .setQuery(materials_SP_ref, bot_row_model.class)
                                                                                                .build();
                                                                                materialSPAdapter = new contentAdapter(mats_SP);
                                                                                rec_material_SP.setAdapter(materialSPAdapter);
                                                                                materialSPAdapter.startListening();
                                                                                materialSPAdapter.setWhenClickListener(new contentAdapter.OnItemsClickListener() {
                                                                                    @Override
                                                                                    public void onItemClick(bot_row_model botRowModel) {
                                                                                        material_SP.setVisibility(View.VISIBLE);
                                                                                        material_SP.setText(botRowModel.getContent());
                                                                                        eig.setVisibility(View.VISIBLE);
                                                                                        eig2.setVisibility(View.VISIBLE);
                                                                                        input.setOnClickListener(new View.OnClickListener() {
                                                                                            @Override
                                                                                            public void onClick(View v) {
                                                                                                String inp1 = String.valueOf(textinput.getText());
                                                                                                mat_amount.setVisibility(View.VISIBLE);
                                                                                                mat_amount.setText(inp1);
                                                                                                textinput.setText("");
                                                                                                nin.setVisibility(View.VISIBLE);
                                                                                                nin2.setVisibility(View.VISIBLE);
                                                                                                input.setOnClickListener(new View.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(View v) {
                                                                                                        String inp2 = String.valueOf(textinput.getText());
                                                                                                        work_amount.setVisibility(View.VISIBLE);
                                                                                                        work_amount.setText(inp2);
                                                                                                        textinput.setText("");
                                                                                                        ten.setVisibility(View.VISIBLE);

                                                                                                        no.setOnClickListener(new View.OnClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(View v) {
                                                                                                                savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                                        material, material_SP, mat_amount, work_amount);


                                                                                                                startActivity(new Intent(getApplicationContext(), CollectDatabyField.class));
                                                                                                            }
                                                                                                        });
                                                                                                        yes.setOnClickListener(new View.OnClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(View v) {
                                                                                                                savedata(fieldname, en, ex, act, cropt, cropr, act_type, asset_use, asset_desc, asset_note, implement,
                                                                                                                        material, material_SP, mat_amount, work_amount);
                                                                                                                recreate();

                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });

                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            });

                                                        }

                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            });
                        }
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


    public void savedata(String fieldname, String en, String ex, TextView act, TextView cropt, TextView cropr, TextView act_type, TextView asset_use, TextView asset_desc, TextView asset_note, TextView implement,
                         TextView material, TextView material_SP, TextView mat_amount, TextView work_amount) {
        String usern = "Sami"; // TODO get name automatically!
        String fieldn = fieldname;
        String ent = en;
        String ext = ex;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long entrylong = getlongDate(ent);
        long exitlong = getlongDate(ext);
        long diff = exitlong - entrylong;





        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        String workT = diffHours + "Hr. " + diffMinutes + "mins. " + diffSeconds + "sec.";


        String ac = String.valueOf(act.getText());
        String acT = String.valueOf(act_type.getText());
        String cr = String.valueOf(cropt.getText());
        String crR = String.valueOf(cropr.getText());
        String As_u = String.valueOf(asset_use.getText());
        String as_d = String.valueOf(asset_desc.getText());
        String ass_n = String.valueOf(asset_note.getText());
        String imp = String.valueOf(implement.getText());
        String mat = String.valueOf(material.getText());
        String matsps = String.valueOf(material_SP.getText());
        String mat_a = String.valueOf(mat_amount.getText());
        String w_am = String.valueOf(work_amount.getText());

        OpDataBind opdata = new OpDataBind(usern, ent, ext, workT, ac, cr, crR, acT, As_u, as_d, ass_n, imp, mat, matsps, mat_a, w_am);
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Operation");
        refer.child(fieldn).child(String.valueOf(entrylong)).child(ac).setValue(opdata);
    }

    public long getlongDate(String t){


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        long timelong = 0;
        try {

            Date d = f.parse(t);
            timelong = d.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timelong;
    }








}