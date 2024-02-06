package com.example.parkingapp.retrofit;

import android.util.Log;

import com.example.parkingapp.api.AuthApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApiInstance {
    private static AuthApi api = null;

    public static AuthApi getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.128.48:8081")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(AuthApi.class);
        }

        Log.d("LOL", "Instance created");

        return api;
    }
}
