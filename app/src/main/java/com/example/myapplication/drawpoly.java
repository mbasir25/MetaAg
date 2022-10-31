package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class drawpoly extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    Button save_map, clear_map;
    CheckBox checkBox;
    EditText field_name;
    Button draw_map;
    boolean valid= true;

    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawpoly);


////
        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        checkBox = findViewById(R.id.checkBox);
        save_map = findViewById(R.id.save_field);
        clear_map = findViewById(R.id.clear_map);
        draw_map= findViewById(R.id.draw_map);
        field_name = findViewById(R.id.field_name);



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
            if (latLngList.size()>0) {
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
            for (Marker marker : markerList) marker.remove();
            latLngList.clear();
            checkBox.setChecked(false);

        });

        save_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField (field_name);
                if (valid & polygon != null) {

                        String name = field_name.getText().toString();

                        filed_holder poly = new filed_holder(polygon);
                        FirebaseDatabase field = FirebaseDatabase.getInstance();
                        DatabaseReference node = field.getReference("Field");

                        node.child(name).setValue(poly);

                        polygon.remove();
                        field_name.setText("");
                        Toast.makeText(drawpoly.this, "Successfully inserted", Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(drawpoly.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

//
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        gMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            Marker marker = gMap.addMarker(markerOptions);
            latLngList.add(latLng);
            markerList.add(marker);

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