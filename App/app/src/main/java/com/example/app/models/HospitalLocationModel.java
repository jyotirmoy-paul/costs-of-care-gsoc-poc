package com.example.app.models;


public class HospitalLocationModel {

    private String hospitalName;
    private LatLng hospitalLocation;
    private int distance;

    public HospitalLocationModel(String hospitalName, LatLng hospitalLocation, int distance) {
        this.hospitalName = hospitalName;
        this.hospitalLocation = hospitalLocation;
        this.distance = distance;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public LatLng getHospitalLocation() {
        return hospitalLocation;
    }

    public int getDistance() {
        return distance;
    }
}
