package com.example.drivezawithfirebase.cars;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dev")
public class DevCarController {

    private final CarService carService;

    public DevCarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/seed-cars")
    public Map<String, Object> seedCars() throws Exception {
        List<Car> cars = List.of(
                make("Toyota", "Prius", 56.9496, 24.1052),
                make("BMW", "X3", 56.9550, 24.1200),
                make("Audi", "A4", 56.9400, 24.0900),
                make("Honda", "Civic", 56.9600, 24.1000),
                make("Nissan", "Leaf", 56.9480, 24.1300)
        );

        int count = 0;
        for (Car c : cars) {
            carService.createCar(c);
            count++;
        }
        return Map.of("seeded", count);
    }

    private Car make(String brand, String model, double lat, double lng) {
        Car c = new Car();
        c.setBrand(brand);
        c.setModel(model);
        c.setStatus("AVAILABLE");
        c.setPricePerKm(0.5);
        c.setPricePerTime(0.2);
        c.setFuelType("Petrol");
        c.setLat(lat);
        c.setLng(lng);
        return c;
    }
}