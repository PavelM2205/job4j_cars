package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.City;
import ru.job4j.cars.repository.CityRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(int id) {
        Optional<City> optCity = cityRepository.findById(id);
        if (optCity.isEmpty()) {
            throw new NoSuchElementException("City is not found");
        }
        return optCity.get();
    }
}
