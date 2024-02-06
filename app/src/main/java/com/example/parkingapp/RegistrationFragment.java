package com.example.parkingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkingapp.model.auth.UserCredentials;
import com.example.parkingapp.util.JWTProvider;
import com.example.parkingapp.util.LoadingCircle;
import com.example.parkingapp.viewModel.AuthViewModel;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private SharedPreferences preferences;

    private AuthViewModel authViewModel;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
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

        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String username = ((TextView)(getView().findViewById(R.id.username))).getText().toString();
                    String password = ((TextView)(getView().findViewById(R.id.password))).getText().toString();
                    String contacts = ((TextView)(getView().findViewById(R.id.contacts))).getText().toString();

                    authViewModel.register(username, password, contacts);
                    Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_authFragment);


                } catch (Exception e) {
                    Log.e("ERROR", e.toString());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }
}