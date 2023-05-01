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
}
