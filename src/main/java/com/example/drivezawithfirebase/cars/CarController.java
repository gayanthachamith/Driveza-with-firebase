package com.example.drivezawithfirebase.cars;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    // USER: nearby cars
    @GetMapping("/cars/nearby")
    public List<Map<String, Object>> nearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "3") double radiusKm,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "distance") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) throws Exception {
        return service.findNearby(lat, lng, radiusKm, q, sort, page, size);
    }

    // TEMP admin create (we’ll lock to ADMIN soon)
    @PostMapping("/admin/cars")
    public Map<String, String> createCar(@RequestBody Car car) throws Exception {
        String id = service.createCar(car);
        return Map.of("id", id);
    }
}