package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class operation extends AppCompatActivity {
    Button data_upload;
    TextView fieldNames;
    private static TinyDB tinydb;
    Gson gson;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        data_upload  = findViewById(R.id.data_upload);
        fieldNames = findViewById(R.id.fieldNames);

        data_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, ?> Entries = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getAll();
                tinydb = new TinyDB(getApplicationContext());

                FirebaseDatabase fieldOperation = FirebaseDatabase.getInstance();
                DatabaseReference operationNode = fieldOperation.getReference("Operation");


                for (Map.Entry<String, ?> get_Entry : Entries.entrySet()) {
//
                    if ("workedFields".equals(get_Entry.getKey())) {
                        ArrayList<String> fieldDetails = tinydb.getListString("workedFields");
                        gson = new Gson();
                        ArrayList<String> fieldsNames = new ArrayList<>();
                        for (String workedField : fieldDetails){

                            Type listType = new TypeToken<List<String>>(){}.getType();

                            List<String> fieldList = gson.fromJson(workedField,listType);

                            String fieldname = fieldList.get(0);
                            String description = fieldList.get(1);
                            WorkedFieldObjMaker[] descriptionList = gson.fromJson(description, WorkedFieldObjMaker[].class);

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
                            WorkedFieldObjMaker enterpair = descriptionList[0];
                            String entString = enterpair.getSecond();
                            Date en = new Date(Long.valueOf(entString));
                            String entry = formatter.format(en);

                            operationNode.child(fieldname).child(entString).setValue(StoreData(descriptionList, fieldname));



                            if (fieldsNames.isEmpty() ){
                                fieldsNames.add(fieldname);

//

//                                !Todo : Solve this issue  same field different time
                            }else {
                                for (int i = 0; i < fieldsNames.size(); i++) {
                                    for (int j = i + 1; j < fieldsNames.size(); j++) {
                                        if (fieldsNames.get(i) != fieldsNames.get(j)) {
                                            fieldsNames.add(fieldname);

//                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
//                                            WorkedFieldObjMaker enterpair = descriptionList[0];
//                                            String entString = enterpair.getSecond();
//                                            Date en = new Date(Long.valueOf(entString));
//                                            String entry = formatter.format(en);
//
//                                            operationNode.child(fieldname).child(entString).setValue(StoreData(descriptionList, fieldname));


                                        } else {
                                            fieldsNames = fieldsNames;

                                        }
                                    }
                                }

                            }
                        }
//
                        String listofFieldString = String.join(", ", fieldsNames);
                        fieldNames.setText(listofFieldString);

                        break;

                    }
                    }
                tinydb.remove("workedFields");

                }


        });


    }


    private Object StoreData(WorkedFieldObjMaker[] descriptionList, String fieldname) {

        FirebaseDatabase fieldOperation = FirebaseDatabase.getInstance();
        DatabaseReference operationNode = fieldOperation.getReference("Operation");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        WorkedFieldObjMaker enterpair = descriptionList[0];
        String entString = enterpair.getSecond();
        Date en = new Date(Long.valueOf(entString));
        String entry = formatter.format(en);

        WorkedFieldObjMaker userpair = descriptionList[1];
        String user = userpair.getSecond();

        WorkedFieldObjMaker exitpair = descriptionList[2];
        String exitString = exitpair.getSecond();
        Date ex = new Date(Long.valueOf(exitString));
        String exit = formatter.format(ex);


        long diff = Long.valueOf(exitString) -Long.valueOf(entString);


        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        String workeTime  = new String(diffHours + "Hr. " +diffMinutes + "mins. "+ diffSeconds +"sec." );



        ObjectEntryExitUserCreator desc = new ObjectEntryExitUserCreator(entry, user, exit, workeTime);

        Map<String, ObjectEntryExitUserCreator> fieldMap= new HashMap<String, ObjectEntryExitUserCreator>();

        fieldMap.put(entry, desc);


       return desc;

    }


}