package com.example.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.models.HospitalModel;

import java.util.ArrayList;

public class HospitalModelAdapter extends RecyclerView.Adapter<HospitalModelAdapter.HospitalDataViewHolder> {

    ArrayList<HospitalModel> list;

    // constructor for this class
    public HospitalModelAdapter(ArrayList<HospitalModel> list){
        this.list = list;
    }

    public class HospitalDataViewHolder extends RecyclerView.ViewHolder {

        TextView textViewHospitalName;
        TextView textViewProcedureName;
        TextView textViewEstimatedCost;

        public HospitalDataViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHospitalName = itemView.findViewById(R.id.txvHospitalName);
            textViewProcedureName = itemView.findViewById(R.id.txvProcedureName);
            textViewEstimatedCost = itemView.findViewById(R.id.txvEstimatedCost);

        }
    }

    @NonNull
    @Override
    public HospitalDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalDataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospital_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalDataViewHolder holder, int position) {

        HospitalModel model = list.get(position);

        holder.textViewHospitalName.setText(model.getHospitalName());
        holder.textViewProcedureName.setText(model.getProcedureName());

        double estimatedCost = model.getEstimatedCost();
        if(estimatedCost == -1){
            holder.textViewEstimatedCost.setText("$ ERX");
        } else{
            holder.textViewEstimatedCost.setText("$ " + estimatedCost);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<HospitalModel> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

}
