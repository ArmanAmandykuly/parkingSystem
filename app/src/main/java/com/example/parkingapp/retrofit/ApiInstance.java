package com.example.parkingapp.retrofit;

import android.util.Log;

import com.example.parkingapp.api.Api;
import com.example.parkingapp.api.AuthApi;

import java.io.IOException;

import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiInstance {
    private static Api api = null;

    public static Api getApi(final String authToken) {
        if (api == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + authToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.128.48:8081")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            api = retrofit.create(Api.class);
        }

        Log.d("LOL", "Instance created");

        return api;
    }
}
