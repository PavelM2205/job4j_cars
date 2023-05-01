package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.City;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CityRepository {
    private final static String FIND_ALL = "From City";
    private final static String FIND_BY_ID = "FROM City WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<City> findAll() {
        return crudRepository.query(FIND_ALL, City.class);
    }

    public Optional<City> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), City.class);
    }
}
