package com.example.parkingapp.api;

import com.example.parkingapp.model.auth.LoginResponse;
import com.example.parkingapp.model.auth.RegCredentials;
import com.example.parkingapp.model.auth.UserCredentials;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/register")
    Call<String> postUserCredentials(@Body RegCredentials userCredentials);

    @POST("/auth/login")
    Call<LoginResponse> attemptLogin(@Body UserCredentials userCredentials);
}
