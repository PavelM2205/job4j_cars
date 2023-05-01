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
}
