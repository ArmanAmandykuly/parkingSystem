package com.example.parkingapp.util;

import com.example.parkingapp.model.Parking;
import com.example.parkingapp.model.ParkingUnit;

import java.util.List;

public class ParkingStatistics {
    private List<Parking> parkingList;

    public ParkingStatistics(List<Parking> parkingList) {
        this.parkingList = parkingList;
    }

    public long countFull() {
        return parkingList.stream().filter(this::isFull).count();
    }

    public boolean isFull(Parking parking) {
        return parking.getParkingUnitList().stream().allMatch(ParkingUnit::isBooked);
    }
}
