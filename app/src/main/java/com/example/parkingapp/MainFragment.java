package com.example.parkingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.parkingapp.adapter.RecyclerViewAdapter;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.util.JWTProvider;
import com.example.parkingapp.util.ListFilter;
import com.example.parkingapp.util.MainRecyclerItemClickListener;
import com.example.parkingapp.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private RecyclerViewAdapter adapter;

    private MainViewModel mainViewModel;

    private ListFilter listFilter;

    private Handler handler;

    public MainFragment() {
        this.adapter = new RecyclerViewAdapter(noParking());
        this.listFilter = new ListFilter(noParking(), adapter);
    }

    public static List<Parking> noParking() {
        return new ArrayList<Parking>(List.of(new Parking("None")));
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        handler = new Handler(Looper.getMainLooper());

        EditText searchView = getView().findViewById(R.id.search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("DEBUGGG", adapter.getItemList().toString());
                mainViewModel.setFilterText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if(Objects.equals(JWTProvider.getJwt(), "None")) {
            Navigation.findNavController(getView()).navigate(R.id.authFragment);
        }

        startFetching();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Objects.equals(JWTProvider.getJwt(), "None")) {
            Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_authFragment);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MainRecyclerItemClickListener(this.getContext(), recyclerView, new MainRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getParkingLiveData().observe(getViewLifecycleOwner(), newData -> {
            listFilter.setItemList(newData);
            listFilter.getFilter().filter(mainViewModel.getFilterText().getValue());
        });

        mainViewModel.getFilterText().observe(getViewLifecycleOwner(), newData -> {
            listFilter.getFilter().filter(newData);
        });
        return view;
    }

    private void startFetching() {
        // Use a Runnable to execute fetchParkings() every 2 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Execute your ViewModel's fetchParkings() method
                mainViewModel.fetchParkings();

                // Post the same Runnable to be executed again after 2 seconds
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }
}