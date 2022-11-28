package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by FreakyJolly on 01-04-2018.
 */

public class Utils {
    private static final String TAG = "UtilsClass";
    final static String KEY_LOCATION_UPDATES_RESULT = "location-update-result";
    public static float accuracy;
    static String addressFragments = "";
    static List<Address> addresses = null;
    public static final long UPDATE_INTERVAL = 5 * 1000;
    public static final float SMALLEST_DISPLACEMENT = 1.0F;
    public static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    public static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 2;
    
    private static GoogleMap gMap;
    private static Polygon polygon;
    private static TinyDB tinydb;

    static String c;
    Context context;

//    Polygon polygon;

//    public static Double lat;
//    public static Double lon;

    

    static void setLocationUpdatesResult(Context context, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_LOCATION_UPDATES_RESULT, value)
                .apply();
    }

    @SuppressLint("MissingPermission")
    public static void getLocationUpdates(final Context context, final Intent intent, String broadcastevent)  {

        LocationResult result = LocationResult.extractResult(intent);
        if (result != null) {

            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            String nowDate = formatter.format(today);


            List<Location> locations = result.getLocations();
            Location firstLocation = locations.get(0);

            getAddress(firstLocation,context);
            checkGeofence(firstLocation, context);
            //firstLocation.getAccuracy();
            //firstLocation.getLatitude();
            //firstLocation.getLongitude();
            //firstLocation.getAccuracy();
            //firstLocation.getSpeed();
            //firstLocation.getBearing();
            LocationRequestHelper.getInstance(context).setValue("locationTextInApp","You are at "+getAddress(firstLocation,context)+
                    "("+nowDate+") with accuracy "+firstLocation.getAccuracy()+" Latitude:"+firstLocation.getLatitude()+" Longitude:"+firstLocation.getLongitude()+
                    " Speed:"+firstLocation.getSpeed()+" Bearing:"+firstLocation.getBearing() +" field   "+ checkGeofence(firstLocation, context));
            showNotificationOngoing(context, broadcastevent,"" +" field   "+ checkGeofence(firstLocation, context));

//            Log.e(TAG, target);
//            new lat long defined here
//            lat =   firstLocation.getLatitude();
//            lon =  firstLocation.getLongitude();






        }
    }


    public static String getAddress(Location location,Context context){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        // Address found using the Geocoder.
        addresses = null;
        Address address = null;
        addressFragments="";
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
            address = addresses.get(0);
        } catch (IOException ioException) {
            Log.e(TAG, "error", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        if (addresses == null || addresses.size()  == 0) {
            Log.i(TAG, "ERORR");
            addressFragments = "NO ADDRESS FOUND";
        } else {
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments = addressFragments+String.valueOf(address.getAddressLine(i));
            }
        }
        LocationRequestHelper.getInstance(context).setValue("addressFragments",addressFragments);
        return addressFragments;
    }

    public static void showNotificationOngoing(Context context, String broadcastevent,String title) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setContentTitle(title + DateFormat.getDateTimeInstance().format(new Date()) + ":" + accuracy)
                .setContentText(addressFragments.toString())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .setStyle(new Notification.BigTextStyle().bigText(addressFragments.toString()))
                .setAutoCancel(true);
        notificationManager.notify(3, notificationBuilder.build());

    }

    public static void removeNotification(Context context){
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


    public static String checkGeofence(Location location, Context context){

        // Problem here ................. discuss !!!!

        tinydb = new TinyDB(context);
        LatLng locate = new LatLng(location.getLatitude(), location.getLongitude());
        ArrayList<String> fieldlist =  tinydb.getListString("cashedpolygons");
        Gson gson = new Gson();

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
//                Log.i(TAG, "checkGeofence: "+s);

            }
////                String[] latLng = s.split(",");
////                double latitude = Double.parseDouble(latLng[0]);
////                double longitude = Double.parseDouble(latLng[1]);
////                LatLng point = new LatLng(latitude, longitude);
////                polyPoints.add(point);
//            }
//            PolygonOptions polygonOptions = new PolygonOptions().addAll(polyPoints);
//            polygon = gMap.addPolygon(polygonOptions);

            Boolean match =  PolyUtil.containsLocation(locate, polyPoints, false);

            if (match == true){
                c =  name;

            }else {
                c =" not in field!! ";

            }


        }


        return c;
    }


//




}

