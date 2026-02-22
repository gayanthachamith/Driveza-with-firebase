package com.example.drivezawithfirebase.rentals.dto;

public class RentRequest {
    private String carId;
    private Double pickupLat;
    private Double pickupLng;
    private String pickupAddress;

    public RentRequest() {}

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public Double getPickupLat() { return pickupLat; }
    public void setPickupLat(Double pickupLat) { this.pickupLat = pickupLat; }

    public Double getPickupLng() { return pickupLng; }
    public void setPickupLng(Double pickupLng) { this.pickupLng = pickupLng; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }
}