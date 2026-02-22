package com.example.drivezawithfirebase.rentals;

public class Rental {
    private String id;
    private String userId;
    private String carId;

    private String status; // ACTIVE, CLOSED

    private long startAt;
    private Double startLat;
    private Double startLng;
    private String startAddress;

    private Long endAt;
    private Double endLat;
    private Double endLng;
    private String endAddress;

    public Rental() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getStartAt() { return startAt; }
    public void setStartAt(long startAt) { this.startAt = startAt; }

    public Double getStartLat() { return startLat; }
    public void setStartLat(Double startLat) { this.startLat = startLat; }

    public Double getStartLng() { return startLng; }
    public void setStartLng(Double startLng) { this.startLng = startLng; }

    public String getStartAddress() { return startAddress; }
    public void setStartAddress(String startAddress) { this.startAddress = startAddress; }

    public Long getEndAt() { return endAt; }
    public void setEndAt(Long endAt) { this.endAt = endAt; }

    public Double getEndLat() { return endLat; }
    public void setEndLat(Double endLat) { this.endLat = endLat; }

    public Double getEndLng() { return endLng; }
    public void setEndLng(Double endLng) { this.endLng = endLng; }

    public String getEndAddress() { return endAddress; }
    public void setEndAddress(String endAddress) { this.endAddress = endAddress; }
}