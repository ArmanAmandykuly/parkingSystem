package com.example.parkingapp.util;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import android.util.Pair;

import com.example.parkingapp.viewModel.AuthViewModel;

public class ServiceDiscovery {

    private final Context context;
    private final NsdManager nsdManager;

    private AuthViewModel authViewModel;

    public ServiceDiscovery(Context context, AuthViewModel authViewModel) {
        this.context = context;
        this.nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        this.authViewModel = authViewModel;
    }

    public void discoverService(String serviceName) {
        NsdManager.DiscoveryListener discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                // Handle failure to start discovery
                authViewModel.getBoolResponse().postValue(new Pair<>(true, false));
                authViewModel.getErrorResponse().postValue("Connection error");
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                // Handle failure to stop discovery
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
                // Discovery started successfully
                authViewModel.getBoolResponse().postValue(new Pair<>(false, false));
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                // Discovery stopped successfully
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                // Service found, get information
                String discoveredServiceName = serviceInfo.getServiceName();
                String discoveredHostAddress = serviceInfo.getHost().getHostAddress();

                Log.d(discoveredServiceName, discoveredHostAddress);

                if (serviceName.equals(discoveredServiceName)) {
                    // Matched service, use discoveredHostAddress

                    authViewModel.getBoolResponse().postValue(new Pair<>(true, false));
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                // Service lost
            }
        };

        nsdManager.discoverServices(serviceName, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopDiscovery() {
        nsdManager.stopServiceDiscovery(null);
    }
}