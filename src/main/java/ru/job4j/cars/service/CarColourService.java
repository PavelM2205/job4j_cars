package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarColour;
import ru.job4j.cars.repository.CarColourRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarColourService {
    private final CarColourRepository carColourRepository;

    public List<CarColour> findAll() {
        return carColourRepository.findAll();
    }

    public CarColour findById(int id) {
        Optional<CarColour> optCarColour = carColourRepository.findById(id);
        if (optCarColour.isEmpty()) {
            throw new NoSuchElementException("CarColour if not found");
        }
        return optCarColour.get();
    }
}
