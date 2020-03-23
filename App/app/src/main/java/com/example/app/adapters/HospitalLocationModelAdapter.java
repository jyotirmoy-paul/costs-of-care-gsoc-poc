package com.example.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.models.HospitalLocationModel;
import com.example.app.models.LatLng;

import java.util.ArrayList;

public class HospitalLocationModelAdapter extends RecyclerView.Adapter<HospitalLocationModelAdapter.HospitalLocationModelViewHolder> {

    private ArrayList<HospitalLocationModel> list;

    public HospitalLocationModelAdapter(ArrayList<HospitalLocationModel> list){
        this.list = list;
    }

    public class HospitalLocationModelViewHolder extends RecyclerView.ViewHolder{

        TextView textViewHospitalName;
        TextView textViewDistance;
        TextView textViewLatLng;

        public HospitalLocationModelViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHospitalName = itemView.findViewById(R.id.txvHospitalName);
            textViewDistance = itemView.findViewById(R.id.txvDistance);
            textViewLatLng = itemView.findViewById(R.id.txvLatLng);

        }
    }

    @NonNull
    @Override
    public HospitalLocationModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalLocationModelViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.hospital_location_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalLocationModelViewHolder holder, int position) {

        HospitalLocationModel model = list.get(position);

        holder.textViewHospitalName.setText(model.getHospitalName());
        holder.textViewDistance.setText(model.getDistance()/1000.0 + " Kms from here");

        LatLng latLng = model.getHospitalLocation();

        String loc = "(" + latLng.getLatitude() + ", " + latLng.getLongitude() + ")";
        holder.textViewLatLng.setText(loc);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
