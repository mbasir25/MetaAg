package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.Map;


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
    private static final String CHANNEL_ID = "Field Exit Channel";
    private  static final int REQ_CODE = 56;

    private static GoogleMap gMap;
    private static Polygon polygon;
    private static TinyDB tinydb;

//    static String c;
    static String text;
    static long t;
    Context context;


//    Polygon polygon;

//    public static Double lat;
//    public static Double lon;

    static Gson objGson = new GsonBuilder().setPrettyPrinting().create();

    static void setLocationUpdatesResult(Context context, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_LOCATION_UPDATES_RESULT, value)
                .apply();
    }

    @SuppressLint("MissingPermission")
    public static void getLocationUpdates(final Context context, final Intent intent)  {

        LocationResult result = LocationResult.extractResult(intent);
        if (result != null) {

            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            String nowDate = formatter.format(today);


            List<Location> locations = result.getLocations();
            Location firstLocation = locations.get(0);


            getAddress(firstLocation,context);

            

                                          // to do that for all fields

            String fieldname = checkGeofence(firstLocation, context);   // checks all polygons returns the last. So, break it and run for whole cashedpolygon
            Pair<String, Long> p = getEntryExitTime(today, fieldname);    // p is a pair of string activity (entry/exit) and long time
            Date entext = new Date(p.second);
            SimpleDateFormat timeformater = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            String entExtTime = timeformater.format(entext);


                //firstLocation.getAccuracy();
                //firstLocation.getLatitude();
                //firstLocation.getLongitude();
                //firstLocation.getAccuracy();
                //firstLocation.getSpeed();
                //firstLocation.getBearing();
                LocationRequestHelper.getInstance(context).setValue("locationTextInApp", "You are at " + getAddress(firstLocation, context) +
                        "(" + nowDate + ") with accuracy " + firstLocation.getAccuracy() + " Latitude:" + firstLocation.getLatitude() + " Longitude:" + firstLocation.getLongitude() +
                        " Speed:" + firstLocation.getSpeed() + " Bearing:" + firstLocation.getBearing() + " field   " + checkGeofence(firstLocation, context) + "  " + p.first + "  " + entExtTime);
//            showNotificationOngoing(context,"" +" field   "+ checkGeofence(firstLocation, context));
                boolean NOTIFY_FLAG = tinydb.getBoolean("NOTIFY_FLAG");

                if (p.first == "Exit Time" && NOTIFY_FLAG == true) {

                    long entry_Time = tinydb.getLong("entry_time");
                    long exit_Time = tinydb.getLong("exit_time");

                    Date entry = new Date(entry_Time);
                    Date exit = new Date(exit_Time);
                    String fieldName = tinydb.getString("field_name");

                    SimpleDateFormat entxtimeformater = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
                    String s_Entry = entxtimeformater.format(entry);
                    String s_Exit = entxtimeformater.format(exit);
//                  make fields object list
                    Map<String, ?> allEntries = PreferenceManager.getDefaultSharedPreferences(context).getAll();
                    boolean WORKFIELDS = false;
                    for (Map.Entry<String, ?> data_Entry : allEntries.entrySet()) {
//                    Log.i("workfields", data_Entry.getKey());
                        if ("workedFields".equals(data_Entry.getKey())) {
//

                            ArrayList<String> workedFieldList = tinydb.getListString("workedFields");
//                                ArrayList<List<String>> newList = new ArrayList<>();
                            List<Pair> newWorkField = listOfWorkinField("Sami", entry_Time, exit_Time);
                            String fieldDescription = objGson.toJson(newWorkField);
                            List<String> fieldWorked = new ArrayList<>();
                            fieldWorked.add(fieldName);
                            fieldWorked.add(fieldDescription);
                            String workedField = objGson.toJson(fieldWorked);
                            workedFieldList.add(workedField);
                            tinydb.putListString("workedFields", workedFieldList);
                            WORKFIELDS = true;
                            break;
                        }
                    }
                    if (WORKFIELDS == false) {
                        ArrayList<String> workedFieldList = new ArrayList<>();
//                                ArrayList<List<String>> newList = new ArrayList<>();
                        List<Pair> newWorkField = listOfWorkinField("Sami", entry_Time, exit_Time);
                        String fieldDescription = objGson.toJson(newWorkField);
                        List<String> fieldWorked = new ArrayList<>();
                        fieldWorked.add(fieldName);
                        fieldWorked.add(fieldDescription);
                        String workedField = objGson.toJson(fieldWorked);
                        workedFieldList.add(workedField);
                        tinydb.putListString("workedFields", workedFieldList);
                    }


                    NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    Notification notification;

                    PendingIntent contentIntent = PendingIntent.getActivity(context, REQ_CODE, new Intent(context, operation.class), PendingIntent.FLAG_UPDATE_CURRENT);

//                    Intent iNotify = new Intent(context, operation.class);
//
//                PendingIntent pi = PendingIntent.getActivity(context, REQ_CODE, iNotify);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        notification = new Notification.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentText("you worked in " + fieldName)
                                .setSubText("Entry at " + s_Entry + " and " + "Exit at " + s_Exit)
                                .setContentIntent(contentIntent)
                                .setChannelId(CHANNEL_ID)
                                .build();

                        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "field channel", NotificationManager.IMPORTANCE_HIGH));
                    } else {
                        notification = new Notification.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentText("you worked in " + fieldName)
                                .setSubText("Entry at " + s_Entry + " and " + "Exit at " + s_Exit)
                                .setContentIntent(contentIntent)
                                .build();
                    }
                    nm.notify(100, notification);
                    tinydb.putBoolean("NOTIFY_FLAG", false);

                }
            



//            ToDo!! if (p.first == "Exit Time"){ Start notification}
//
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

//    public static void showNotificationOngoing(Context context,String title) {
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
//                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder notificationBuilder = new Notification.Builder(context)
//                .setContentTitle(title + DateFormat.getDateTimeInstance().format(new Date()) + ":" + accuracy)
//                .setContentText(addressFragments.toString())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(contentIntent)
//                .setOngoing(true)
//                .setStyle(new Notification.BigTextStyle().bigText(addressFragments.toString()))
//                .setAutoCancel(true);
//        notificationManager.notify(3, notificationBuilder.build());
//
//    }
//
//    public static void removeNotification(Context context){
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
//    }


    public static String checkGeofence(Location location, Context context){

        // Problem here ................. discuss !!!!

        tinydb = new TinyDB(context);
        LatLng locate = new LatLng(location.getLatitude(), location.getLongitude());
        ArrayList<String> fieldlist =  tinydb.getListString("cashedpolygons");
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
//

            }
////

            Boolean match =  PolyUtil.containsLocation(locate, polyPoints, false);

            if (match == true){
                c =  name;

                break;


            }else {
                c =" not in field!! ";

            }




        }



        return c;
    }


    public static Pair<String, Long> getEntryExitTime(Date date, String fieldName){
        long mills = date.getTime();
        long prevMills = tinydb.getLong("prevtime");


        if (prevMills == 0 ) {
            tinydb.putLong("prevtime", mills);
            tinydb.putBoolean("PREV_IN_FIELD", false);
            tinydb.putString("prevField" , "");
        } else{
            boolean PREV_IN_FIELD = tinydb.getBoolean("PREV_IN_FIELD");
//            String prevField = tinydb.getString("prevField");
//            boolean PREV_IN_FIELD = tinydb.getBoolean("PREV_IN_FIELD");
            if (fieldName != " not in field!! " && PREV_IN_FIELD == false ){
                    long entryTime = (mills - 2000);
                    tinydb.putBoolean("PREV_IN_FIELD", true);
                    tinydb.putLong("entry_time", entryTime);
                    tinydb.putString("field_name", fieldName);
                    tinydb.putBoolean("NOTIFY_FLAG", true);
                    t = entryTime;
                    text = "Entry Time";
                    tinydb.putString("prevField" , fieldName);
                }
            else if (fieldName != " not in field!! " && PREV_IN_FIELD == true){
                String prevField = tinydb.getString("prevField");
                boolean transition = fieldName.equals(prevField);
                if (transition == false){
                    long exitTime =  mills;  //  !TODO should be mills, problem in setting exit time for field1 and entry for field 2 if transition
                    t = exitTime;
                    text = "Exit Time";
                    tinydb.putString("prevField" , fieldName );
                    tinydb.putBoolean("PREV_IN_FIELD", false);
                    tinydb.putString("field_name", prevField);
                    tinydb.putLong("exit_time", exitTime);
                } else {
                    tinydb.putBoolean("PREV_IN_FIELD", true);
                    tinydb.putString("prevField" , fieldName );
                    t = tinydb.getLong("entry_time");
                    text = "Entry Time";
                }

            }


           else if (fieldName == " not in field!! " && PREV_IN_FIELD == true){
                long exitTime =  (mills + prevMills)/2;
                tinydb.putBoolean("PREV_IN_FIELD", false);
                tinydb.putLong("exit_time", exitTime);
                t = exitTime;
                text = "Exit Time";
                tinydb.putString("prevField" , "");

           }







            tinydb.putLong("prevtime", mills);
//            tinydb.putBoolean("PREV_IN_FIELD", false);

        }

        return new Pair<String, Long>(text, t);


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


//




}

