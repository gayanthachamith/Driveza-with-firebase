package com.example.drivezawithfirebase.cars;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {
    private final Firestore db;

    public CarService(Firestore db) {
        this.db = db;
    }

    public String createCar(Car car) throws Exception {
        if (car.getStatus() == null || car.getStatus().isBlank()) {
            car.setStatus("AVAILABLE");
        }
        var doc = db.collection("cars").document();
        car.setId(doc.getId());
        doc.set(car).get();
        return car.getId();
    }

    public List<Map<String, Object>> findNearby(double lat, double lng, double radiusKm,
                                                String q, String sort,
                                                int page, int size) throws Exception {

        var snaps = db.collection("cars")
                .whereEqualTo("status", "AVAILABLE")
                .get().get();

        List<Map<String, Object>> list = new ArrayList<>();

        for (QueryDocumentSnapshot doc : snaps) {
            Car car = doc.toObject(Car.class);
            car.setId(doc.getId());

            double dist = haversineKm(lat, lng, car.getLat(), car.getLng());
            if (dist <= radiusKm) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("id", car.getId());
                row.put("brand", car.getBrand());
                row.put("model", car.getModel());
                row.put("pricePerKm", car.getPricePerKm());
                row.put("pricePerTime", car.getPricePerTime());
                row.put("fuelType", car.getFuelType());
                row.put("lat", car.getLat());
                row.put("lng", car.getLng());
                row.put("distanceKm", dist);
                list.add(row);
            }
        }

        // search
        if (q != null && !q.isBlank()) {
            String qq = q.toLowerCase();
            list.removeIf(r -> {
                String model = String.valueOf(r.getOrDefault("model", "")).toLowerCase();
                String brand = String.valueOf(r.getOrDefault("brand", "")).toLowerCase();
                return !(model.contains(qq) || brand.contains(qq));
            });
        }

        // sort
        if ("price".equalsIgnoreCase(sort)) {
            list.sort(Comparator.comparingDouble(r -> ((Number) r.get("pricePerKm")).doubleValue()));
        } else {
            list.sort(Comparator.comparingDouble(r -> ((Number) r.get("distanceKm")).doubleValue()));
        }

        // paginate
        int from = Math.max(0, page * size);
        int to = Math.min(list.size(), from + size);
        if (from >= list.size()) return List.of();
        return list.subList(from, to);
    }

    private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}