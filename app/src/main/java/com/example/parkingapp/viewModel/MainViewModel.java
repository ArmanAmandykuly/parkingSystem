package com.example.parkingapp.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.parkingapp.model.Parking;
import com.example.parkingapp.model.repositories.ParkingRepository;
import com.example.parkingapp.util.HostApiProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final ParkingRepository parkingRepository;

    private MutableLiveData<List<Parking>> _parkingLiveData = new MutableLiveData<>();

    private MutableLiveData<String> _filterText = new MutableLiveData<>("");

    private MutableLiveData<String> clickedLocation = new MutableLiveData<>(null);

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public MainViewModel() {

        this.parkingRepository = new ParkingRepository();

        fetchParkings();
    }

    public MutableLiveData<List<Parking>> getParkingLiveData() {
        return _parkingLiveData;
    }

    public MutableLiveData<String> getFilterText() {
        return _filterText;
    }

    public void setFilterText(String filterText) {
        this.getFilterText().postValue(filterText);
    }

    public void fetchParkings() {
        Call<List<Parking>> client = parkingRepository.getParkings();

        client.enqueue(new Callback<List<Parking>>() {
            @Override
            public void onResponse(Call<List<Parking>> call, Response<List<Parking>> response) {
                if(response.isSuccessful()) {
                    _parkingLiveData.postValue(response.body());
                    Log.d(this.getClass().toString(),response.body().get(0).getParkingUnitList().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Parking>> call, Throwable t) {
                Log.e("ERROR", "Could not fetch data");
            }
        });
    }

    public Parking getParking(String location) {
        return _parkingLiveData.getValue().stream().filter(parking -> parking.getLocation().equals(location)).findFirst().get();
    }

    public void bookParking(long id) {
        Call<String> client = parkingRepository.bookParking(id);

        client.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    Log.d("Success","");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<String> getClickedLocation() {
        return clickedLocation;
    }

    public void setClickedLocation(MutableLiveData<String> clickedLocation) {
        this.clickedLocation = clickedLocation;
    }
}