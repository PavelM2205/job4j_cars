package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBody;
import ru.job4j.cars.repository.CarBodyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarBodyService {
    private final CarBodyRepository carBodyRepository;

    public CarBody create(CarBody carBody) {
        Optional<CarBody> optCarBody = carBodyRepository.create(carBody);
        if (optCarBody.isEmpty()) {
            throw new IllegalStateException("The CarBody has not added");
        }
        return optCarBody.get();
    }

    public List<CarBody> findAll() {
        return carBodyRepository.findAll();
    }

    public CarBody findById(int id) {
        Optional<CarBody> optCarBody = carBodyRepository.findById(id);
        if (optCarBody.isEmpty()) {
            throw new NoSuchElementException("CarBody is not found");
        }
        return optCarBody.get();
    }

    public void delete(int id) {
        carBodyRepository.delete(id);
    }

    public void update(CarBody carBody) {
        carBodyRepository.update(carBody);
    }
}
