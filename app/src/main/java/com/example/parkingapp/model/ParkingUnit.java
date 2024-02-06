package com.example.parkingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class ParkingUnit {
    private long id;

    @SerializedName("booked")
    private boolean isBooked;

    private int number;

    public ParkingUnit(long id, int number, boolean isBooked) {
        this.id = id;
        this.isBooked = isBooked;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}