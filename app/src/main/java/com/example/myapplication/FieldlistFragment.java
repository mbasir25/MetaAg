package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<FieldListDataModel> fieldnameholder;

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
        recyclerView = view.findViewById(R.id.field_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fieldnameholder = new ArrayList<>();

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
}