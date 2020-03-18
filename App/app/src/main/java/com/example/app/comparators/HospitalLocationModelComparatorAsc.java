package com.example.app.comparators;

import com.example.app.models.HospitalLocationModel;

import java.util.Comparator;

public class HospitalLocationModelComparatorAsc implements Comparator<HospitalLocationModel> {
    @Override
    public int compare(HospitalLocationModel o1, HospitalLocationModel o2) {
        return o1.getDistance() - o2.getDistance();
    }
}
