package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarColour;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarColourRepository {
    private final static String FIND_ALL = "FROM CarColour";
    private final static String FIND_BY_ID = "FROM CarColour WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<CarColour> findAll() {
        return crudRepository.query(FIND_ALL, CarColour.class);
    }

    public Optional<CarColour> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), CarColour.class);
    }
}
