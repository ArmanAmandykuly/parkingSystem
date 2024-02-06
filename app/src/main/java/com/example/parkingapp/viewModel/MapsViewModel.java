package com.example.parkingapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsViewModel extends ViewModel {
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>("");

    public MutableLiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public void setTitleLiveData(MutableLiveData<String> titleLiveData) {
        this.titleLiveData = titleLiveData;
    }
}
