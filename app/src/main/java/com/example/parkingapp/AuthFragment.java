package com.example.parkingapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.parkingapp.model.auth.UserCredentials;
import com.example.parkingapp.util.JWTProvider;
import com.example.parkingapp.util.LoadingCircle;
import com.example.parkingapp.util.ServiceDiscovery;
import com.example.parkingapp.viewModel.AuthViewModel;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {

    private SharedPreferences preferences;

    private AuthViewModel authViewModel;

    private LoadingCircle loadingCircle;

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance(String param1, String param2) {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        authViewModel.getErrorResponse().postValue(null);

        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        preferences = getActivity().getSharedPreferences("cookie", Context.MODE_PRIVATE);

        loadingCircle = new LoadingCircle(requireContext(), requireActivity());

        getView().findViewById(R.id.button).setOnClickListener(view -> {
            try {
                String username = ((TextView)(getView().findViewById(R.id.username))).getText().toString();
                String password = ((TextView)(getView().findViewById(R.id.password))).getText().toString();

                authViewModel.signIn(username, password);

                authViewModel.getBoolResponse().observe(getViewLifecycleOwner(), booleanBooleanPair -> {
                    Log.d("DEBUG", booleanBooleanPair.toString());
                    if(!booleanBooleanPair.first) {
                        loadingCircle.showLoadingDialog();
                    } else {
                        loadingCircle.hideLoadingDialog();
                    }
                    if(booleanBooleanPair.second) {
                        preferences.edit().putString("jwtToken", JWTProvider.getJwt());
                        Navigation.findNavController(getView()).navigate(R.id.mapsFragment);
                    }
                });

                authViewModel.getErrorResponse().observe(getViewLifecycleOwner(), errorMessage -> {
                    TextView errorView = getView().findViewById(R.id.errorMessage);
                    Log.d("DEBUG1", authViewModel.getBoolResponse().toString());
                    if(errorMessage != null) {
                        errorView.setText((CharSequence) errorMessage);
                        errorView.setVisibility(View.VISIBLE);
                    }
                });

            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

}