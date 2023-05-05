package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Year;
import ru.job4j.cars.repository.YearRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class YearService {
    private final YearRepository yearRepository;

    public Year create(Year year) {
        Optional<Year> optYear = yearRepository.create(year);
        if (optYear.isEmpty()) {
            throw new IllegalStateException("The Year has not added");
        }
        return optYear.get();
     }

        public List<Year> findAll() {
        return yearRepository.findAll();
    }

    public Year findById(int id) {
        Optional<Year> optYear = yearRepository.findById(id);
        if (optYear.isEmpty()) {
            throw new NoSuchElementException("Year is not found");
        }
        return optYear.get();
    }

    public void update(Year year) {
        yearRepository.update(year);
    }

    public void delete(int id) {
        yearRepository.delete(id);
    }
}
