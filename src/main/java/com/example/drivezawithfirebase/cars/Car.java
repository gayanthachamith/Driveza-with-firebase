package com.example.drivezawithfirebase.cars;

public class Car {
    private String id;
    private String brand;
    private String model;
    private String status;      // AVAILABLE, RENTED, RETURNED, UNAVAILABLE
    private double pricePerKm;
    private double pricePerTime;
    private String fuelType;
    private double lat;
    private double lng;

    public Car() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPricePerKm() { return pricePerKm; }
    public void setPricePerKm(double pricePerKm) { this.pricePerKm = pricePerKm; }

    public double getPricePerTime() { return pricePerTime; }
    public void setPricePerTime(double pricePerTime) { this.pricePerTime = pricePerTime; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }
}