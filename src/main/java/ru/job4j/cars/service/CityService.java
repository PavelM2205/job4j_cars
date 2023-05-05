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

    public City create(City city) {
        Optional<City> optCity = cityRepository.create(city);
        if (optCity.isEmpty()) {
            throw new IllegalStateException("The City has not added");
        }
        return optCity.get();
    }

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

    public void update(City city) {
        cityRepository.update(city);
    }

    public void delete(int id) {
        cityRepository.delete(id);
    }
}
