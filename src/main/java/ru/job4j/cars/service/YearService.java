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
}
