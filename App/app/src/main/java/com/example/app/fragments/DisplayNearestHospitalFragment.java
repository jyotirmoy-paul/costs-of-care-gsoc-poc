package com.example.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapters.HospitalLocationModelAdapter;
import com.example.app.comparators.HospitalLocationModelComparatorAsc;
import com.example.app.models.HospitalLocationModel;
import com.example.app.utils.NearbyHospitals;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayNearestHospitalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.display_nearest_hospital_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // fetch all the nearby hospitals
        ArrayList<HospitalLocationModel> list = NearbyHospitals.getHospitals();

        Collections.sort(list, new HospitalLocationModelComparatorAsc());

        HospitalLocationModelAdapter adapter = new HospitalLocationModelAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;

    }
}
