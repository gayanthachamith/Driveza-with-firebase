package com.example.drivezawithfirebase.rentals;

import com.example.drivezawithfirebase.common.ApiException;
import com.example.drivezawithfirebase.cars.Car;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RentalService {

    private final Firestore db;

    public RentalService(Firestore db) {
        this.db = db;
    }

    public Rental getMyActiveRental(String userId) throws Exception {
        var snaps = db.collection("rentals")
                .whereEqualTo("userId", userId)
                .whereEqualTo("status", "ACTIVE")
                .get().get();

        if (snaps.isEmpty()) return null;

        var doc = snaps.getDocuments().get(0);
        Rental r = doc.toObject(Rental.class);
        r.setId(doc.getId());
        return r;
    }

    public Map<String, Object> rentCar(String userId, String carId,
                                       Double pickupLat, Double pickupLng, String pickupAddress) throws Exception {

        // Rule: user can only have 1 active rental
        if (getMyActiveRental(userId) != null) {
            throw new ApiException(400, "You already have an active rental. Return it first.");
        }

        var carRef = db.collection("cars").document(carId);
        var carSnap = carRef.get().get();
        if (!carSnap.exists()) {
            throw new ApiException(404, "Car not found.");
        }

        Car car = carSnap.toObject(Car.class);
        car.setId(carSnap.getId());

        if (!"AVAILABLE".equalsIgnoreCase(car.getStatus())) {
            throw new ApiException(400, "Car is not available for rent.");
        }

        // Create rental
        Rental rental = new Rental();
        rental.setUserId(userId);
        rental.setCarId(carId);
        rental.setStatus("ACTIVE");
        rental.setStartAt(System.currentTimeMillis());
        rental.setStartLat(pickupLat);
        rental.setStartLng(pickupLng);
        rental.setStartAddress(pickupAddress);

        var rentalRef = db.collection("rentals").document();
        rental.setId(rentalRef.getId());

        // Update car status -> RENTED
        carRef.update(Map.of("status", "RENTED")).get();
        // Save rental
        rentalRef.set(rental).get();

        return Map.of("rentalId", rental.getId(), "carId", carId);
    }

    public Map<String, Object> returnCar(String userId, String rentalId,
                                         Double dropLat, Double dropLng, String dropAddress) throws Exception {

        var rentalRef = db.collection("rentals").document(rentalId);
        var rentalSnap = rentalRef.get().get();

        if (!rentalSnap.exists()) throw new ApiException(404, "Rental not found.");

        Rental rental = rentalSnap.toObject(Rental.class);
        rental.setId(rentalSnap.getId());

        if (!userId.equals(rental.getUserId())) {
            throw new ApiException(403, "You cannot return someone else's rental.");
        }

        if (!"ACTIVE".equalsIgnoreCase(rental.getStatus())) {
            throw new ApiException(400, "Rental is not active.");
        }

        // close rental
        rentalRef.update(Map.of(
                "status", "CLOSED",
                "endAt", System.currentTimeMillis(),
                "endLat", dropLat,
                "endLng", dropLng,
                "endAddress", dropAddress
        )).get();

        // set car back to AVAILABLE
        db.collection("cars").document(rental.getCarId())
                .update(Map.of("status", "AVAILABLE")).get();

        return Map.of("returned", true, "carId", rental.getCarId());
    }
}