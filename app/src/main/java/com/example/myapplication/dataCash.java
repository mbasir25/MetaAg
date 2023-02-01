package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class dataCash extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    TinyDB tinydb;


    String fileName = "your_file_name.csv";


    ArrayList<String> fields;
    ArrayList<String> act_types;
    ArrayList<String> act_and_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_cash);



        Button cashchat = findViewById(R.id.chat);
        Button download = findViewById(R.id.download);

        ListView showdata = findViewById(R.id.showdata);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Operation");
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

                        for (DataSnapshot fnames: task.getResult().getChildren()){
                            String fieldn = fnames.getKey();

                            for (DataSnapshot en : fnames.getChildren()){
                                for (DataSnapshot ac : en.getChildren()){
                                    OpDataBind data = ac.getValue(OpDataBind.class);
//                                    !TODO Write here


                                    String actName  = data.getActivity();
                                    String actType  = data.getActivity_type();
                                    String cropName  = data.getCrop();
                                    String cropRate  = data.getCroprate();
                                    String pUnit  = data.getAsset_used();
                                    String pUnitDEsc  = data.getAsset_description();
                                    String pNote  = data.getAsset_note();
                                    String implementName  = data.getImplement();
                                    String materialName  = data.getMaterial();
                                    String  matType  = data.getMaterial_type();
                                    String  matAmount  = data.getMaterial_amount();
                                    String workNote  = data.getWork_amount();
                                    String operator  = data.getOperator();
                                    String entryTime  = data.getEntry();
                                    String opTime  = data.getDuration();
                                    String exitTime  = data.getExit();

                                    String [] dataArray = {fieldn, operator, entryTime,opTime,exitTime, actName,actType,cropName,cropRate,pUnit,pUnitDEsc,pNote,implementName,materialName,matType,matAmount,workNote};
                                    dataList.add(dataArray);
                                }


                            }
                        }
                        download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fileName = "Filed_Activity_Data.csv";
                                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), fileName);
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
                    }

                } else {
                    Toast.makeText(dataCash.this, "Sorry bro!!!", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}
