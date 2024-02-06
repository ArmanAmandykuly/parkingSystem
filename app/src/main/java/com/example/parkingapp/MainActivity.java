package com.example.parkingapp;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Set the NavigationItemSelectedListener
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle item clicks here
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                navigateToFragment(R.id.mainFragment);
            } else if (itemId == R.id.nav_registration) {
                navigateToFragment(R.id.registrationFragment);
            } else if (itemId == R.id.nav_about) {
                navigateToFragment(R.id.aboutFragment);
            } else if(itemId == R.id.nav_login) {
                navigateToFragment(R.id.authFragment);
            } else if(itemId == R.id.nav_maps) {
                navigateToFragment(R.id.mapsFragment);
            }

            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        disableAutomaticNightModeTransition();
    }

    private void disableAutomaticNightModeTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);

            if (uiModeManager != null) {
                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
            }
        } else {
            // For versions earlier than Android Q, you may need to handle this differently
            // For example, you can set the theme manually based on time of day
        }
    }


    private void navigateToFragment(int fragmentId) {
        // Get the NavController associated with the NavHostFragment
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        // Navigate to the specified fragment
        navController.navigate(fragmentId);
    }
}