package com.example.parkingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.parkingapp.model.Parking;
import com.example.parkingapp.util.ParkingStatistics;
import com.example.parkingapp.viewModel.MainViewModel;
import com.example.parkingapp.viewModel.MapsViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private MapView mapView;
    private GoogleMap map;

    private MainViewModel mainViewModel;

    private MapsViewModel mapsViewModel;

    private ParkingStatistics statistics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapsView);

        if(mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        mainViewModel.getParkingLiveData().observe(getViewLifecycleOwner(), new Observer<List<Parking>>() {
            @Override
            public void onChanged(List<Parking> parkingList) {
                // Update the map with parking data
                updateMapWithParkingData(parkingList, view);
            }
        });
    }

    void updateMapWithParkingData(List<Parking> parkingList, View view) {
        map.clear();

        // Update the map with parking data
        if (parkingList != null) {
            statistics = new ParkingStatistics(parkingList);
            TextView textView = view.findViewById(R.id.fullText);
            textView.setText(String.valueOf(statistics.countFull()) + "/" + String.valueOf(parkingList.size()));
            for (Parking parking : parkingList) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(parking.getGpsLat(), parking.getGpsLng()))
                        .title(parking.getLocation()))
                        .showInfoWindow();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;

        map.setOnMarkerClickListener(this);

        mainViewModel.fetchParkings();

        List<Parking> parkingList = mainViewModel.getParkingLiveData().getValue();

        if(parkingList != null) {

            for (Parking parking : parkingList) {
                map.addMarker(new MarkerOptions().position(new LatLng(parking.getGpsLat(), parking.getGpsLng())).title(parking.getLocation()));
            }
        } else {
            Log.d("WHAT DA HELL", "SERIOUSLY");
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.2380, 76.8829), 12));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mapsViewModel.getTitleLiveData().postValue(marker.getTitle());
        TextView textView = this.requireView().findViewById(R.id.titleText);
        textView.setText(marker.getTitle());
        Button button = this.requireView().findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.bookingFragment);
            mainViewModel.getClickedLocation().postValue(marker.getTitle());
        });
        return false;
    }
}