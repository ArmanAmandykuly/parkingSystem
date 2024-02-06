package com.example.parkingapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class JWTProvider {
    private static String jwt;

    public JWTProvider() {

    }

    public static String getJwt() {
        if(jwt == null) {
            return "None";
        }
        return jwt;
    }

    public static void setJwt(String jwt) {
        JWTProvider.jwt = jwt;
    }
}
