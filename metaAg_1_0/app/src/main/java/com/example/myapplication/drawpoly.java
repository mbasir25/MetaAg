package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import io.grpc.internal.JsonParser;

public class drawpoly extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    Button save_map, clear_map;
    FloatingActionButton fshowpoly;
    CheckBox checkBox;
    EditText field_name;
    Button draw_map;
    boolean valid = true;
    TinyDB tinydb;
    Polygon polygon = null;
    Polygon polyg = null;
    ArrayList<Polygon> polyglist;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationProviderClient;


    //
    Gson objGson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawpoly);


////
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        checkBox = findViewById(R.id.checkBox);
        save_map = findViewById(R.id.save_field);
        clear_map = findViewById(R.id.clear_map);
        draw_map = findViewById(R.id.draw_map);
        field_name = findViewById(R.id.field_name);
        fshowpoly= findViewById(R.id.fshowpoly);

        tinydb = new TinyDB(getApplicationContext());


        checkBox.setOnCheckedChangeListener((compoundButton, ticked) -> {
            if (polygon == null) return;

            if (ticked) {
                polygon.setFillColor(Color.BLUE);
            } else {
                polygon.setFillColor(Color.TRANSPARENT);
            }

        });


        draw_map.setOnClickListener(view -> {
            if (polygon != null) {
                polygon.remove();
            }
            if (latLngList.size() > 0) {
                PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList)
                        .clickable(true);

                polygon = gMap.addPolygon(polygonOptions);
                //color
                polygon.setFillColor(Color.BLUE);
                polygon.setStrokeColor(Color.BLUE);

                if (checkBox.isChecked()) {
                    polygon.setFillColor(Color.BLUE);
                }
            }

        });


        clear_map.setOnClickListener(view -> {
            if (polygon != null) {
                polygon.remove();

            }

            if (polyglist!= null && polyglist.size()!=0){
                for (Polygon polig : polyglist){
                    polig.remove();
                }
            }
            for (Marker marker : markerList) marker.remove();
            latLngList.clear();

            checkBox.setChecked(false);

            fshowpoly.setEnabled(true);

        });

        save_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(field_name);
                if (valid & polygon != null) {

                    String name = field_name.getText().toString();

                    filed_holder poly = new filed_holder(polygon);
                    FirebaseDatabase field = FirebaseDatabase.getInstance();
                    DatabaseReference node = field.getReference("Field");

                    node.child(name).setValue(poly);


                    Context context = getApplicationContext();
                    TinyDB tinydb = new TinyDB(context);
                    // TODO: Find a more efficient way to fetch the old record.
                    // TODO: Search if the shared preference storage clear after app killed?
// CRUD operation room database, check internet connectivity in android
                    Map<String, ?> allEntries = PreferenceManager.getDefaultSharedPreferences(context).getAll();
                    boolean fieldStored = false;
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        Log.d("map values", entry.getKey());
                        if ("cashedpolygons".equals(entry.getKey())) {
//                              using tinydb for accessing sharedpreference

                            ArrayList<String> fieldlist = tinydb.getListString("cashedpolygons");
//                                ArrayList<List<String>> newList = new ArrayList<>();
                            List<String> newfield = listOfField(name, latLngList);
                            String newf = objGson.toJson(newfield);
                            fieldlist.add(newf);
//                                String newWholeList = objGson.toJson(fieldlist);
//                                fieldlist.add(newWholeList);
                            tinydb.putListString("cashedpolygons", fieldlist);
                            fieldStored = true;

                            break;


//                                List<String> listedField = listOfField(name, latLngList);
//
//                                ArrayList<Object> object = tinydb.getListObject("cashedpolygons", filed_holder.class);
//
//
//                                tinydb.putListObject("cashedpolygons", object);
//
//
//                                // the value exits; what to do now????
////                                Set<String> set = LocationRequestHelper.getInstance(context).getValue("key", null);
//                                Gson gson = new Gson();
//                                SharedPreferences mSharedPreferences = context.getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
//                                String json = mSharedPreferences.getString("cashedpolygons", "");
//                                JsonParser parser = new JsonParser();
//                                JsonArray array = parser.parse(json).getAsJsonArray();


//                                for(int i=0; i< array.size(); i++){
//                                    array.add(gson.fromJson(array.get(i), array.class));
//                                }

///
                        }
                    }

                    if (fieldStored == false) {
                        ArrayList<String> newfieldList = new ArrayList<>();
//                        ArrayList<List<String>> newList = new ArrayList<>();
                        List<String> newfield = listOfField(name, latLngList);
                        String newf = objGson.toJson(newfield);
                        newfieldList.add(newf);
//                        newList.add(newfield);
//                        String newWholeList = objGson.toJson(newList);
//                        newfieldList.add(newWholeList);
                        tinydb.putListString("cashedpolygons", newfieldList);
                    }


                    polygon.remove();
                    field_name.setText("");
                    Toast.makeText(drawpoly.this, "Successfully inserted", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(drawpoly.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


        fshowpoly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, ?> allEntries = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getAll();

                polyglist = new ArrayList<>();

                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    Log.d("map values", entry.getKey());
                    if ("cashedpolygons".equals(entry.getKey())) {
//                              using tinydb for accessing sharedpreference

                        ArrayList<String> fieldlist = tinydb.getListString("cashedpolygons");
                        Gson gson = new Gson();

                        String c = new String();

                        for (String field : fieldlist){

                            Type listType = new TypeToken<List<String>>(){}.getType();

                            List<String> list = gson.fromJson(field,listType);

                            String name = list.get(0);
                            String latlng = list.get(1);
                            ModelFieldObjectCreator[] latLngObj = gson.fromJson(latlng, ModelFieldObjectCreator[].class);

                            List<LatLng> polyPoints = new ArrayList<>();
                            for (ModelFieldObjectCreator s : latLngObj) {
                                Double lat = s.getLatitude();
                                Double lng = s.getLongitude();
                                LatLng point = new LatLng(lat,lng);
                                polyPoints.add(point);

                            }
                            if (polyPoints.size() > 0) {
                                PolygonOptions polygonOps = new PolygonOptions().addAll(polyPoints)
                                        .clickable(true);

                                polyg = gMap.addPolygon(polygonOps);
                                //color
                                polyg.setStrokeColor(Color.BLUE);
                                polyg.setStrokeWidth(5);
                                polyglist.add(polyg);
                            }

                        }
                    }
                }

                fshowpoly.setEnabled(false);
            }
        });

//
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng l = new LatLng(40.430486, -86.917207);
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l, 10));
        gMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            Marker marker = gMap.addMarker(markerOptions);
            latLngList.add(latLng);
            markerList.add(marker);

        });

    }

    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;

        }
        return valid;
    }

    public List<String> listOfField(String name, List latLngList) {
        String jsonLatLng = objGson.toJson(latLngList);
        ArrayList<String> field = new ArrayList<>();
        field.add(name);
        field.add(jsonLatLng);
//
        return field;
    }



}