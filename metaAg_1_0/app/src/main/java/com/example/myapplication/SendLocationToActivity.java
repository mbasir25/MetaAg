package com.example.myapplication;

import android.location.Location;

public class SendLocationToActivity {
    private Location location;

    public SendLocationToActivity(Location mLocation) {
        this.location = location;

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
