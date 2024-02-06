package com.example.parkingapp.viewModel;

import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parkingapp.model.auth.LoginResponse;
import com.example.parkingapp.model.auth.RegCredentials;
import com.example.parkingapp.model.auth.UserCredentials;
import com.example.parkingapp.retrofit.AuthApiInstance;
import com.example.parkingapp.util.JWTProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    public AuthViewModel() {

    }

    private MutableLiveData<Pair<Boolean, Boolean>> boolResponse = new MutableLiveData<>(new Pair<>(false, false));
    private MutableLiveData<String> errorResponse = new MutableLiveData<>(null);

    public void signIn(String username, String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        boolResponse = new MutableLiveData<>(new Pair<>(false, false));
        Call<LoginResponse> call = AuthApiInstance.getApi().attemptLogin(credentials);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    JWTProvider.setJwt(response.body().getMessage());
                    boolResponse.postValue(new Pair<>(true, true));
                } else if(response.code() / 100 == 4) {
                    errorResponse.postValue("Wrong credentials");
                    boolResponse.postValue(new Pair<>(true, false));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                boolResponse.postValue(new Pair<>(true, false));
                errorResponse.postValue(t.getMessage());
            }
        });
    }

    public void register(String username, String password, String contacts) {
        RegCredentials credentials = new RegCredentials(username, password, contacts);
        Call<String> call = AuthApiInstance.getApi().postUserCredentials(credentials);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() != null)
                    Log.d("Response", response.body().toString());
                else
                    Log.e("ERROR", response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Not registered", "");
            }
        });
    }

    public MutableLiveData<Pair<Boolean, Boolean>> getBoolResponse() {
        return boolResponse;
    }

    public MutableLiveData<String> getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(MutableLiveData<String> errorResponse) {
        this.errorResponse = errorResponse;
    }
}
