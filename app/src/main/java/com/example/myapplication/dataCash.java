package com.example.myapplication;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.internal.maps.zzad;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dataCash extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    DatabaseReference mfields;
    TinyDB tinydb;
    GoogleMap gMap;
    Gson gson;


    String fileName = "your_file_name.csv";


    ArrayList<String> fields;
    ArrayList<String> act_types;
    ArrayList<String> act_and_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_cash);



        Button cashchat = findViewById(R.id.chat);
        Button emaildata = findViewById(R.id.emaildata);
        Button download = findViewById(R.id.download);
        Button polygcash = findViewById(R.id.polygcash);

        ListView showdata = findViewById(R.id.showdata);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Operation");
        mfields = mFirebaseDatabase.getReference("Field");
        fields = new ArrayList<String>();

        ArrayList<String[]> dataList = new ArrayList<>();






        act_types = new ArrayList<String>();
        act_and_type = new ArrayList<String>();



        mDatabaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(dataCash.this, "Read data Successfully!", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot name : task.getResult().getChildren()) {
                            String fname = name.getKey();
                            fields.add(fname);

                        }

                        cashchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ArrayAdapter<String> fieledAdapter = new ArrayAdapter<String>(getApplicationContext(), com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item, fields);
                                showdata.setAdapter(fieledAdapter);

                                showdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String name = fieledAdapter.getItem(position);
                                        Intent intent = new Intent(dataCash.this, ViewopbyDate.class);
                                        intent.putExtra("fNAME", name);
                                        startActivity(intent);

                                    }
                                });
                            }
                        });

                        for (DataSnapshot fnames : task.getResult().getChildren()) {

                            String fieldn = fnames.getKey();
                            for (DataSnapshot en : fnames.getChildren()) {
                                for (DataSnapshot ac : en.getChildren()) {
                                    OpDataBind data = ac.getValue(OpDataBind.class);
//                                    !TODO Write here


                                    String actName = data.getActivity();
                                    String actType = data.getActivity_type();
                                    String cropName = data.getCrop();
                                    String cropRate = data.getCroprate();
                                    String pUnit = data.getAsset_used();
                                    String pUnitDEsc = data.getAsset_description();
                                    String pNote = data.getAsset_note();
                                    String implementName = data.getImplement();
                                    String materialName = data.getMaterial();
                                    String matType = data.getMaterial_type();
                                    String matAmount = data.getMaterial_amount();
                                    String workNote = data.getWork_amount();
                                    String operator = data.getOperator();
                                    String entryTime = data.getEntry();
                                    String opTime = data.getDuration();
                                    String exitTime = data.getExit();

                                    String[] dataArray = {fieldn, operator, entryTime, opTime, exitTime, actName, actType, cropName, cropRate, pUnit, pUnitDEsc, pNote, implementName, materialName, matType, matAmount, workNote};
                                    dataList.add(dataArray);
                                }
                            }
                        }

                        download.setOnClickListener(new View.OnClickListener() {



                            @Override
                            public void onClick(View v) {
                                String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
                                String currentTime = new SimpleDateFormat("HH_mm_ss", Locale.getDefault()).format(new Date());
                                fileName = currentDate +"_"+currentTime+"_act_data.csv";
                                String Dir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath();
                                File file = new File(Dir,fileName);




                                try {

                                    FileWriter fileWriter = new FileWriter(file);
                                    CSVWriter csvWriter = new CSVWriter(fileWriter);

                                    String[] headers = {"Field_Name", "Operator", "Entry_Time", "Duration", "Exit_Time", "Activity_Name", "Activity_Type", "Crop_Name", "Crop_Rate", "Power_Unit", "Power_Unit_description", "Note_Power_unit", "Implement_Name", "Material_Used", "Material_Type", "Material_Amount", "Extra_Note"};
                                    csvWriter.writeNext(headers);

                                    for (String[] dataarray : dataList) {
                                        csvWriter.writeNext(dataarray);


                                    }

                                    csvWriter.close();
                                    fileWriter.close();
                                    Toast.makeText(dataCash.this, "Data exported successfully!! Dir: InternalStorage/Documents", Toast.LENGTH_LONG).show();
                                }catch (IOException e) {
                                    e.printStackTrace();

                                }
                            }
                        });

                        emaildata.setOnClickListener(new View.OnClickListener() {     //!todo file provider should be used.
                            @Override
                            public void onClick(View v) {

                                String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
                                String currentTime = new SimpleDateFormat("HH_mm_ss", Locale.getDefault()).format(new Date());
                                fileName = currentDate +"_"+currentTime+"_act_data.csv";
                                String Dir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath();
                                File file = new File(Dir,fileName);




                                try {

                                    FileWriter fileWriter = new FileWriter(file);
                                    CSVWriter csvWriter = new CSVWriter(fileWriter);

                                    String[] headers = {"Field_Name", "Operator", "Entry_Time", "Duration", "Exit_Time", "Activity_Name", "Activity_Type", "Crop_Name", "Crop_Rate", "Power_Unit", "Power_Unit_description", "Note_Power_unit", "Implement_Name", "Material_Used", "Material_Type", "Material_Amount", "Extra_Note"};
                                    csvWriter.writeNext(headers);


                                    for (String[] dataarray : dataList) {
                                        csvWriter.writeNext(dataarray);


                                    }

                                    csvWriter.close();
                                    fileWriter.close();
                                    Toast.makeText(dataCash.this, "Data exported successfully!! Dir: InternalStorage/Documents", Toast.LENGTH_LONG).show();
                                }catch (IOException e) {
                                    e.printStackTrace();

                                }
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/csv");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "CSV Data");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Dir+fileName));
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"samifpm13@gmail.com"});

                                startActivity(Intent.createChooser(intent, "Send Email"));

                            }
                        });

                    }

                } else {
                    Toast.makeText(dataCash.this, "Sorry bro!!!", Toast.LENGTH_SHORT).show();
                }
            }


        });

        ArrayList<String> newfieldList = new ArrayList<>();
        mfields.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        for (DataSnapshot name : task.getResult().getChildren()) {
                            ArrayList< String> field = new ArrayList<>();
                            String fieldname = name.getKey();
                            filed_holder poly = new filed_holder(name.getValue());
                            HashMap polyg = (HashMap) poly.getPolygon();
                            HashMap hash = (HashMap) polyg.get("polygon");
                            List points = (List) hash.get("points");

//                                    TinyDB tDB = new TinyDB(getApplicationContext());
                            List<String> onefield = listOfField(fieldname, points);
                            String newf = gson.toJson(onefield);
                            newfieldList.add(newf);

                        }
                        tinydb = new TinyDB(getApplicationContext());
                        polygcash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tinydb.putListString("cashedpolygons", newfieldList);
                                Toast.makeText(dataCash.this, "Fields boundery cashed", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }else {
                    Toast.makeText(dataCash.this, "Problem encountered ! ", Toast.LENGTH_SHORT).show();  // !TODO solve this issue. showing pronlem
                }
            }
        });



    }
    public List<String> listOfField(String name, List latLngList) {
        gson = new Gson();
        String jsonLatLng = gson.toJson(latLngList);
        ArrayList<String> field = new ArrayList<>();
        field.add(name);
        field.add(jsonLatLng);
//
        return field;
    }
}
