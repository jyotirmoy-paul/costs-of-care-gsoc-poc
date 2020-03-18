package com.example.app.comparators;

import com.example.app.models.HospitalModel;

import java.util.Comparator;

public class HospitalModelComparatorDesc implements Comparator<HospitalModel> {

    @Override
    public int compare(HospitalModel o1, HospitalModel o2) {
        return Double.compare(o2.getEstimatedCost(), o1.getEstimatedCost());
    }
}