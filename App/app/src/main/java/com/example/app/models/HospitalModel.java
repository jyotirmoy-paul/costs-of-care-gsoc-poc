package com.example.app.models;

import com.google.gson.annotations.SerializedName;

public class HospitalModel {

    @SerializedName("hospital_name")
    private String hospitalName;

    @SerializedName("procedure_name")
    private String procedureName;

    @SerializedName("estimated_cost")
    private double estimatedCost;

    public String getHospitalName() {
        return hospitalName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }
}
