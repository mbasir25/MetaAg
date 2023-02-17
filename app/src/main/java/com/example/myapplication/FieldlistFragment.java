package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FieldlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FieldlistFragment extends Fragment implements SelectListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TinyDB tinydb;
    Gson gson;
    boolean valid = true;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    EditText ent_date, ent_time, ext_date, ext_time, namef;
    ArrayList<FieldListDataModel> fieldnameholder;
    int en_hr, en_min, ex_hr, ex_min;

    DatePickerDialog.OnDateSetListener listener_en;
    DatePickerDialog.OnDateSetListener listener_ex;

    public FieldlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FieldlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FieldlistFragment newInstance(String param1, String param2) {
        FieldlistFragment fragment = new FieldlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fieldlist, container, false);
        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.add);
        LinearLayout lay = (LinearLayout) view.findViewById(R.id.glayout);
        Button addField = (Button)view.findViewById(R.id.addField);
        recyclerView = view.findViewById(R.id.field_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fieldnameholder = new ArrayList<>();

        ent_date =view.findViewById(R.id.ent_date);
        ent_time =view.findViewById(R.id.ent_time);
        ext_date =view.findViewById(R.id.ext_date);
        ext_time =view.findViewById(R.id.ext_time);
        namef = view.findViewById(R.id.namef);

        Calendar en_calendar =  Calendar.getInstance();
        final int en_year = en_calendar.get(Calendar.YEAR);
        final int en_month = en_calendar.get(Calendar.MONTH);
        final int en_day = en_calendar.get(Calendar.DAY_OF_MONTH);





        Calendar ex_calendar =  Calendar.getInstance();
        final int ex_year = ex_calendar.get(Calendar.YEAR);
        final int ex_month = ex_calendar.get(Calendar.MONTH);
        final int ex_day = ex_calendar.get(Calendar.DAY_OF_MONTH);

        ent_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month  = month+1;
                        String en_date = year + "-" + month + "-" + dayOfMonth;
                        ent_date.setText(en_date);
                    }
                }, en_year, en_month, en_day);
                datePickerDialog.show();
            }
        });

        ext_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month  = month+1;
                        String ex_date = year + "-" + month + "-" + dayOfMonth;
                        ext_date.setText(ex_date);
                    }
                }, ex_year, ex_month, ex_day);
                datePickerDialog.show();
            }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lay.getVisibility()== View.GONE){
                    lay.setVisibility(View.VISIBLE);
                }else{
                    lay.setVisibility(View.GONE);
                }
            }
        });



        ent_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        en_hr = hourOfDay;
                        en_min = minute;

                        String en_hrs = String.format("%02d", en_hr);
                        String en_mins = String.format("%02d", en_min);
                        String en_time = en_hrs +"." +en_mins+ ".00";
                        ent_time.setText(en_time);

                    }
                }, 24, 0, true);
                timePickerDialog.updateTime(en_hr, en_min);
                timePickerDialog.show();
            }
        });


        ext_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ex_hr = hourOfDay;
                        String ex_hrs = String.format("%02d", ex_hr);
                        ex_min = minute;
                        String ex_mins = String.format("%02d", ex_min);
//                        String ex_time = "{:02d}:{:02d}".format(String.valueOf(ex_hrs),ex_min);

                        String ex_time = ex_hrs +"." +ex_mins+ ".00";
                        ext_time.setText(ex_time);

                    }
                }, 24, 00, true);
                timePickerDialog.updateTime(ex_hr, ex_min);
                timePickerDialog.show();
            }
        });


        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkField(namef);
                checkField(ent_time);
                checkField(ent_date);
                checkField(ext_date);
                checkField(ext_time);

                if (valid){
                String name = namef.getText().toString();
                String en_time = ent_time.getText().toString();
                String en_date = ent_date.getText().toString();
                String ex_time = ext_time.getText().toString();
                String ex_date = ext_date.getText().toString();
                String en = en_date + " "+en_time;
                Long en_l = getlongDate(en);
                String ex = ex_date+ " "+ex_time;
                Long ex_l = getlongDate(ex);



                    Map<String, ?> Entries = PreferenceManager.getDefaultSharedPreferences(getContext()).getAll();
                    tinydb = new TinyDB(getContext());
                    boolean WORKFIELDS = false;
                    for (Map.Entry<String, ?> data_Entry : Entries.entrySet()) {
//                    Log.i("workfields", data_Entry.getKey());
                        if ("workedFields".equals(data_Entry.getKey())) {
//

                            ArrayList<String> workedFieldList = tinydb.getListString("workedFields");
//                                ArrayList<List<String>> newList = new ArrayList<>();
                            List<Pair> newWorkField = listOfWorkinField("Sami", en_l, ex_l);
                            String fieldDescription = gson.toJson(newWorkField);
                            List<String> fieldWorked = new ArrayList<>();
                            fieldWorked.add(name);
                            fieldWorked.add(fieldDescription);
                            String workedField = gson.toJson(fieldWorked);
                            workedFieldList.add(workedField);
                            tinydb.putListString("workedFields", workedFieldList);
                            WORKFIELDS = true;
                            break;
                        }
                    }
                    if (WORKFIELDS == false) {
                        ArrayList<String> workedFieldList = new ArrayList<>();
//                                ArrayList<List<String>> newList = new ArrayList<>();
                        List<Pair> newWorkField = listOfWorkinField("Sami", en_l, ex_l);
                        String fieldDescription = gson.toJson(newWorkField);
                        List<String> fieldWorked = new ArrayList<>();
                        fieldWorked.add(name);
                        fieldWorked.add(fieldDescription);
                        String workedField = gson.toJson(fieldWorked);
                        workedFieldList.add(workedField);
                        tinydb.putListString("workedFields", workedFieldList);
                    }
                    lay.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "successfully added", Toast.LENGTH_SHORT).show();
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.detach(FieldlistFragment.this).attach(FieldlistFragment.this).commit();
                    getActivity().recreate();

                }else{
                    Toast.makeText(getContext(), "Insert correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });




        Map<String, ?> Entries = PreferenceManager.getDefaultSharedPreferences(getContext()).getAll();
        tinydb = new TinyDB(getContext());

        for (Map.Entry<String, ?> get_Entry : Entries.entrySet()) {
//
            if ("workedFields".equals(get_Entry.getKey())) {
                ArrayList<String> fieldDetails = tinydb.getListString("workedFields");
                gson = new Gson();

                for (String workedField : fieldDetails) {

                    Type listType = new TypeToken<List<String>>() {
                    }.getType();

                    List<String> fieldList = gson.fromJson(workedField, listType);
                    String fieldname = fieldList.get(0);

                    String description = fieldList.get(1);
                    WorkedFieldObjMaker[] descriptionList = gson.fromJson(description, WorkedFieldObjMaker[].class);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
                    WorkedFieldObjMaker enterpair = descriptionList[0];
                    String entString = enterpair.getSecond();
                    Date en = new Date(Long.valueOf(entString));
                    String entry = formatter.format(en);

                    WorkedFieldObjMaker exitpair = descriptionList[2];
                    String exitString = exitpair.getSecond();
                    Date ex = new Date(Long.valueOf(exitString));
                    String exit = formatter.format(ex);

                    FieldListDataModel fields = new FieldListDataModel(fieldname,entry,exit);
                    fieldnameholder.add(fields);

                }
            }
        }
            // !TODO   only one field is showing up, also make them clickable!





        recyclerView.setAdapter(new fieldnameAdapter(fieldnameholder, this));
        return view;

    }



    @Override
    public void onItemClicked(FieldListDataModel fieldListDataModel) {
            String field = fieldListDataModel.getFieldname();
            String entry = fieldListDataModel.getEntry();
            String exit = fieldListDataModel.getExit();

            tinydb.putString("fieldname", field);
            tinydb.putString("entry", entry);
            tinydb.putString("exit", exit);

            startActivity(new Intent(getContext(), data_wizard.class));





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
    public static ArrayList listOfWorkinField(String user, Long entTime, Long extTime){
        Gson gson = new Gson();

        Pair<String, Long> ent_time =  new Pair<>("Entry time", entTime);
        Pair<String, String> username = new Pair<>("user", user);
        Pair<String, Long> ext_time =  new Pair<>("Exit time", extTime);
        List<Pair> fieldDetail = new ArrayList<>();
        fieldDetail.add(ent_time);
        fieldDetail.add(username);
        fieldDetail.add(ext_time);
        return (ArrayList) fieldDetail;

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