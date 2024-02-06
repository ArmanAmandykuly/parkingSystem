package com.example.parkingapp.api;

import com.example.parkingapp.model.Parking;

import java.util.List;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("/parking")
    Call<List<Parking>> getParkings();

    @POST("/parking")
    Call<String> bookParking(@Query("id") long id);
}
