package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapters.HospitalDataAdapter;
import com.example.app.comparators.HospitalModelComparatorDesc;
import com.example.app.interfaces.JsonPlaceHolderApi;
import com.example.app.models.HospitalLocationModel;
import com.example.app.models.HospitalModel;
import com.example.app.utils.Constants;
import com.example.app.utils.NearbyHospitals;
;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    ArrayList<HospitalModel> listOfHospitals;
    HospitalDataAdapter adapter;

    SeekBar seekBar;
    TextView textViewMaxPrice;

    LinearLayout linearLayoutFilterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // referencing to the views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button searchButton = findViewById(R.id.btnSearch);
        EditText editTextProcedureName = findViewById(R.id.edtProcedureName);
        Button buttonSortAscendingLocation = findViewById(R.id.btnSortAscendingLocation);
        Button buttonFilter = findViewById(R.id.btnFilter);

        seekBar = findViewById(R.id.seekBar);
        textViewMaxPrice = findViewById(R.id.txvMaxPrice);
        linearLayoutFilterLayout = findViewById(R.id.filterParentLayout);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        // setup the recycler view and adapters
        listOfHospitals = new ArrayList<>();
        adapter = new HospitalDataAdapter(listOfHospitals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        NearbyHospitals nearbyHospitals = new NearbyHospitals(10000); // radius in meters

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String procedureName = editTextProcedureName.getText().toString().trim();

                if(procedureName.length() < 3 || procedureName.length() > 200){
                    editTextProcedureName.setError("search within 4 - 200 characters");
                    editTextProcedureName.requestFocus();
                    return;
                }

                // clear the list of previous queries
                listOfHospitals.clear();

                // check all the nearby hospitals for the procedure
                for(HospitalLocationModel loc : nearbyHospitals.getHospitals()){
                    displayHospitalsForProcedure(loc.getHospitalName(), procedureName);
                }

            }
        });


        buttonSortAscendingLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sortAscendingLocation();

            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleFilterView((Button) v);

            }
        });

    }

    private void sortAscendingLocation(){
        Toast.makeText(MainActivity.this,
                "This will work when fetching real location data - using latitude and longitude", Toast.LENGTH_LONG).show();
    }

    private void toggleFilterView(Button b){
        if(Constants.BUTTON_FILTER.equals(b.getText().toString())){
            // start filtering

            setListenerForFilter();

            linearLayoutFilterLayout.setVisibility(View.VISIBLE);
            seekBar.setProgress(100);

            b.setText(Constants.BUTTON_NO_FILTER);
        } else{
            // stop filtering

            linearLayoutFilterLayout.setVisibility(View.GONE);
            adapter.filterList(listOfHospitals);

            b.setText(Constants.BUTTON_FILTER);
        }
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
    * Makes RESTFUL api call and fetches all hospitals according to the passed parameters
    * */
    private void displayHospitalsForProcedure(String hospitalName, String procedureName){
        jsonPlaceHolderApi.getHospitalsList(hospitalName, procedureName)
                .enqueue(new Callback<List<HospitalModel>>() {
                    @Override
                    public void onResponse(Call<List<HospitalModel>> call, Response<List<HospitalModel>> response) {

                        if(!response.isSuccessful()){

                            // todo: handle accordingly
                            // for sample purpose, just display a toast message
                            Toast.makeText(MainActivity.this,
                                    "Failure Code: " + response.code(), Toast.LENGTH_SHORT).show();

                            return;

                        }

                        assert response.body() != null;

                        listOfHospitals.addAll(response.body());


                        // notify data set changed to update the UI
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<HospitalModel>> call, Throwable t) {

                        // todo: handle failure
                        // for sample purpose, just display a toast message
                        Toast.makeText(MainActivity.this,
                                t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
