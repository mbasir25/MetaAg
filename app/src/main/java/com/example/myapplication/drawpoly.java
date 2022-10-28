package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class drawpoly extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    Button save_map, clear_map;
    CheckBox checkBox;
//
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
        save_map = findViewById(R.id.save_map);
        clear_map = findViewById(R.id.clear_map);

        checkBox.setOnCheckedChangeListener((compoundButton, ticked) -> {
            if (polygon == null) return;

            if (ticked) {
               polygon.setFillColor(Color.BLUE);
           } else {
               polygon.setFillColor(Color.TRANSPARENT);
           }

        });
        save_map.setOnClickListener(view -> {
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

//
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            Marker marker = gMap.addMarker(markerOptions);
            latLngList.add(latLng);
            markerList.add(marker);

        });

    }

//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//
//    }

}