package com.example.parkingapp.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Parking {
    private String location;

    private double gpsLat;

    private double gpsLng;

    private List<ParkingUnit> parkingUnitList;

    public Parking(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return getLocation();
    }

    public List<ParkingUnit> getParkingUnitList() {
        return parkingUnitList;
    }

    public void setParkingUnitList(List<ParkingUnit> parkingUnitList) {
        this.parkingUnitList = parkingUnitList;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public double getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(double gpsLng) {
        this.gpsLng = gpsLng;
    }
}
