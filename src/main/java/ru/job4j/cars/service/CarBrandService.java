package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.CarBrandRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarBrandService {
    private final CarBrandRepository carBrandRepository;

    public CarBrand create(CarBrand carBrand) {
        Optional<CarBrand> optCarBrand = carBrandRepository.create(carBrand);
        if (optCarBrand.isEmpty()) {
            throw new IllegalStateException("The CarBrand has not added");
        }
        return optCarBrand.get();
    }

    public List<CarBrand> findAll() {
        return carBrandRepository.findAll();
    }

    public CarBrand findById(int id) {
        Optional<CarBrand> optCarBrand = carBrandRepository.findById(id);
        if (optCarBrand.isEmpty()) {
            throw new NoSuchElementException("CarBrand is not found");
        }
        return optCarBrand.get();
    }

    public void update(CarBrand carBrand) {
        carBrandRepository.update(carBrand);
    }

    public void delete(int id) {
        carBrandRepository.delete(id);
    }
}
