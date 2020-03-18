package com.example.app.models;

import android.location.Location;

public class HospitalLocationModel {

    private String hospitalName;
    private Location hospitalLocation;
    private int distance;

    public String getHospitalName() {
        return hospitalName;
    }

    public HospitalLocationModel(String hospitalName, Location hospitalLocation, int distance) {
        this.hospitalName = hospitalName;
        this.hospitalLocation = hospitalLocation;
        this.distance = distance;
    }
}
