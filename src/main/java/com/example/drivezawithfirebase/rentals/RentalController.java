package com.example.drivezawithfirebase.rentals;

import com.example.drivezawithfirebase.rentals.dto.RentRequest;
import com.example.drivezawithfirebase.rentals.dto.ReturnRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @GetMapping("/my-active")
    public Rental myActive(Authentication auth) throws Exception {
        String userId = (String) auth.getPrincipal(); // uid from FirebaseTokenFilter
        return service.getMyActiveRental(userId);
    }

    @PostMapping
    public Map<String, Object> rent(Authentication auth, @RequestBody RentRequest req) throws Exception {
        String userId = (String) auth.getPrincipal();

        return service.rentCar(
                userId,
                req.getCarId(),
                req.getPickupLat(),
                req.getPickupLng(),
                req.getPickupAddress()
        );
    }

    @PostMapping("/{id}/return")
    public Map<String, Object> ret(Authentication auth, @PathVariable("id") String rentalId,
                                   @RequestBody ReturnRequest req) throws Exception {
        String userId = (String) auth.getPrincipal();

        return service.returnCar(
                userId,
                rentalId,
                req.getDropLat(),
                req.getDropLng(),
                req.getDropAddress()
        );
    }
}