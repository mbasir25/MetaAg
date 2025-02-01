package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationRequestHelper {


    private static LocationRequestHelper mSharedPreferenceUtils;
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    private LocationRequestHelper(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized LocationRequestHelper getInstance(Context context) {

        if (mSharedPreferenceUtils == null) {
            mSharedPreferenceUtils = new LocationRequestHelper(context.getApplicationContext());
        }
        return mSharedPreferenceUtils;
    }

    /**
     * Stores String value in preference
     */
    public void setValue(String key, String value) {
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.commit();
    }

    /**
     * Stores int value in preference
     */
    public void setValue(String key, int value) {
        mSharedPreferencesEditor.putInt(key, value);
        mSharedPreferencesEditor.commit();
    }

    /**
     * Stores Double value in String format in preference
     */
    public void setValue(String key, double value) {
        setValue(key, Double.toString(value));
    }

    /**
     * Stores long value in preference
     */
    public void setValue(String key, long value) {
        mSharedPreferencesEditor.putLong(key, value);
        mSharedPreferencesEditor.commit();
    }
// store lists

    /**
     * Stores boolean value in preference
     */
    public void setValue(String key, boolean value) {
        mSharedPreferencesEditor.putBoolean(key, value);
        mSharedPreferencesEditor.commit();
    }

    /**
     * Retrieves String value from preference
     */
    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * Retrieves int value from preference
     */
    public int getIntValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Retrieves long value from preference
     */
    public long getLongValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Retrieves boolean value from preference
     */
    public boolean getBooleanValue(String keyFlag, boolean defaultValue) {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue);
    }

    /**
     * Removes key from preference
     *
     * @param key key of preference that is to be deleted
     */
    public void removeKey(String key) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor.remove(key);
            mSharedPreferencesEditor.commit();
        }
    }

    /**
     * Clears all the preferences stored
     */
    public void clear() {
        mSharedPreferencesEditor.clear().commit();
    }

//    public void setValue(String key, filed_holder poly) {
//        Set<String> set = new HashSet<String>();
//        set.addAll((Collection<? extends String>) poly);
//        mSharedPreferencesEditor.putStringSet(key, set);
//        mSharedPreferencesEditor.commit();

//    }

//    public Set<String> getValue(String key, Object poly) {
//
//    }
//
//
    public void setValue(String key, List<LatLng> latLngList) {
        Gson gson = new Gson();
        String json = gson.toJson(latLngList);
        mSharedPreferencesEditor.putString(key, json);
        mSharedPreferencesEditor.commit();
    }

    public Set<String> getValue(String key, Object o) {
        return mSharedPreferences.getStringSet(key, null);
    }
}
