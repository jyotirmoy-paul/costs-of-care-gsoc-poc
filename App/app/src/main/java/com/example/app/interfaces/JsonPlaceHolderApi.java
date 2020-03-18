package com.example.app.interfaces;

import com.example.app.models.HospitalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("hospitals/hospital_name={hospital_name}&procedure_name={procedure_name}/")
    Call<List<HospitalModel>> getHospitalsList(
            @Path("hospital_name") String hospitalName,
            @Path("procedure_name") String procedureName);

}
