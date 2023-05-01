package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car create(Car car) {
        Optional<Car> optCar = carRepository.create(car);
        if (optCar.isEmpty()) {
            throw new IllegalStateException("The car has not added");
        }
        return optCar.get();
    }

    public void update(Car car) {
        carRepository.update(car);
    }

    public void delete(int id) {
        carRepository.delete(id);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(int id) {
        Optional<Car> optCar = carRepository.findById(id);
        if (optCar.isEmpty()) {
            throw new NoSuchElementException("Car is not found");
        }
        return optCar.get();
    }
}
