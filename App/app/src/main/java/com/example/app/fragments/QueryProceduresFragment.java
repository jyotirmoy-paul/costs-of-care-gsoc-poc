package com.example.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapters.HospitalModelAdapter;
import com.example.app.comparators.HospitalModelComparatorDesc;
import com.example.app.interfaces.JsonPlaceHolderApi;
import com.example.app.models.HospitalLocationModel;
import com.example.app.models.HospitalModel;
import com.example.app.utils.Constants;
import com.example.app.utils.NearbyHospitals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryProceduresFragment extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private ArrayList<HospitalModel> listOfHospitals;
    private HospitalModelAdapter adapter;

    private SeekBar seekBar;
    private TextView textViewMaxPrice;

    private LinearLayout linearLayoutFilterLayout;

    private boolean filterShow = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.query_procedures_fragment, container, false);

        // referencing to the views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ImageView searchButton = view.findViewById(R.id.btnSearch);
        EditText editTextProcedureName = view.findViewById(R.id.edtProcedureName);
        ImageView buttonFilter = view.findViewById(R.id.btnFilter);

        seekBar = view.findViewById(R.id.seekBar);
        textViewMaxPrice = view.findViewById(R.id.txvMaxPrice);
        linearLayoutFilterLayout = view.findViewById(R.id.filterParentLayout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        // setup the recycler view and adapters
        listOfHospitals = new ArrayList<>();
        adapter = new HospitalModelAdapter(listOfHospitals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {

            String procedureName = editTextProcedureName.getText().toString().trim();

            if(procedureName.length() < 3 || procedureName.length() > 200){
                editTextProcedureName.setError("search within 4 - 200 characters");
                editTextProcedureName.requestFocus();
                return;
            }

            // clear the list of previous queries
            listOfHospitals.clear();

            // check all the nearby hospitals for the procedure
            for(HospitalLocationModel loc : NearbyHospitals.getHospitals()){
                displayHospitalsForProcedure(loc.getHospitalName(), procedureName);
            }

        });



        buttonFilter.setOnClickListener(v -> toggleFilterView());

        return view;
    }

    private void toggleFilterView(){
        if(filterShow){
            // start filtering

            setListenerForFilter();

            linearLayoutFilterLayout.setVisibility(View.VISIBLE);
            seekBar.setProgress(100);

        } else{
            // stop filtering

            linearLayoutFilterLayout.setVisibility(View.GONE);
            adapter.filterList(listOfHospitals);

        }
        filterShow = !filterShow;
    }

    private void setListenerForFilter(){

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int priceValue;

                if(progress < 70){
                    priceValue = 10 + progress*(progress*10);
                } else{
                    priceValue = progress*5000;
                }

                textViewMaxPrice.setText("$ " + priceValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progress = seekBar.getProgress();
                int priceValue;

                if(progress < 70){
                    priceValue = 10 + progress*(progress*10);
                } else{
                    priceValue = progress*5000;
                }

                ArrayList<HospitalModel> list = new ArrayList<>();

                for(HospitalModel model : listOfHospitals){
                    if(model.getEstimatedCost() < priceValue){
                        list.add(model);
                    }
                }

                Collections.sort(list, new HospitalModelComparatorDesc());

                adapter.filterList(list);

            }
        });


    }

    /*
     * The following method makes RESTFUL api call and fetches all hospitals as per the passed parameters
     * */
    private void displayHospitalsForProcedure(String hospitalName, String procedureName){
        jsonPlaceHolderApi.getHospitalsList(hospitalName, procedureName)
                .enqueue(new Callback<List<HospitalModel>>() {
                    @Override
                    public void onResponse(Call<List<HospitalModel>> call, Response<List<HospitalModel>> response) {

                        if(!response.isSuccessful()){

                            // todo: handle accordingly
                            // for sample purpose, just display a toast message
                            Toast.makeText(getContext(),
                                    "Failure Code: " + response.code(), Toast.LENGTH_SHORT).show();

                            return;

                        }

                        assert response.body() != null;

                        listOfHospitals.addAll(response.body());


                        // notify data set changed to update the UI
                        adapter.filterList(listOfHospitals);
                        seekBar.setProgress(100);
                        textViewMaxPrice.setText("$500000");

                    }

                    @Override
                    public void onFailure(Call<List<HospitalModel>> call, Throwable t) {

                        // todo: handle failure
                        // for sample purpose, just display a toast message
                        Toast.makeText(getContext(),
                                t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
