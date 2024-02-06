package com.example.parkingapp.util;

import androidx.lifecycle.MutableLiveData;

public class HostApiProvider {
    private static MutableLiveData<String> hostApi;

    public static MutableLiveData<String> getHostApi() {
        return hostApi;
    }

    public static void setHostApi(MutableLiveData<String> hostApi) {
        HostApiProvider.hostApi = hostApi;
    }
}
