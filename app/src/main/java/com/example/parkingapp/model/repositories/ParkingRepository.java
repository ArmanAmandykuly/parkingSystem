package com.example.parkingapp.model.repositories;

import com.example.parkingapp.api.Api;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.retrofit.ApiInstance;
import com.example.parkingapp.util.JWTProvider;

import java.util.List;

import retrofit2.Call;

public class ParkingRepository {
    private final Api api;

    public ParkingRepository() {
        api = ApiInstance.getApi(JWTProvider.getJwt());
    }

    public Call<List<Parking>> getParkings() {
        return api.getParkings();
    }

    public Call<String> bookParking(long id) {
        return api.bookParking(id);
    }
}
