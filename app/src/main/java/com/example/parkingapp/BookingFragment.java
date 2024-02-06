package com.example.parkingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkingapp.adapter.BlockRecyclerViewAdapter;
import com.example.parkingapp.adapter.RecyclerViewAdapter;
import com.example.parkingapp.viewModel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    private MainViewModel mainViewModel;

    private BlockRecyclerViewAdapter adapter;

    public BookingFragment() {
        // Required empty public constructor
    }


    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new BlockRecyclerViewAdapter(mainViewModel.getParking(mainViewModel.getClickedLocation().getValue()).getParkingUnitList());

        RecyclerView recyclerView = requireView().findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
    }
}