package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapters.HospitalDataAdapter;
import com.example.app.interfaces.JsonPlaceHolderApi;
import com.example.app.models.HospitalModel;
import com.example.app.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        listOfHospitals = new ArrayList<>();
        adapter = new HospitalDataAdapter(listOfHospitals);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        try{
            displayHospitalsForProcedure("penn", "Heart");
        } catch (Exception e){
            Toast.makeText(this,
                    "Exception Occured", Toast.LENGTH_LONG).show();
            Log.i("MAIN_ACTIVITY", e.toString());
        }


    }

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
