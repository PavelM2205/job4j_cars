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

    public CarColour create(CarColour carColour) {
        Optional<CarColour> optCarColour = carColourRepository.create(carColour);
        if (optCarColour.isEmpty()) {
            throw new IllegalStateException("The CarColour has not added");
        }
        return optCarColour.get();
    }

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

    public void update(CarColour carColour) {
        carColourRepository.update(carColour);
    }

    public void delete(int id) {
        carColourRepository.delete(id);
    }
}
