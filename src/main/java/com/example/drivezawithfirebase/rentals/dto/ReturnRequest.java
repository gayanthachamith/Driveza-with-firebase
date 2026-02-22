package com.example.drivezawithfirebase.rentals.dto;

public class ReturnRequest {
    private Double dropLat;
    private Double dropLng;
    private String dropAddress;

    public ReturnRequest() {}

    public Double getDropLat() { return dropLat; }
    public void setDropLat(Double dropLat) { this.dropLat = dropLat; }

    public Double getDropLng() { return dropLng; }
    public void setDropLng(Double dropLng) { this.dropLng = dropLng; }

    public String getDropAddress() { return dropAddress; }
    public void setDropAddress(String dropAddress) { this.dropAddress = dropAddress; }
}