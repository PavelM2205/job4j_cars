package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarBodyRepository {
    private final static String FIND_ALL = "FROM CarBody";
    private final static String FIND_BY_ID = "FROM BodyCar WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<CarBody> findAll() {
        return crudRepository.query(FIND_ALL, CarBody.class);
    }

    public Optional<CarBody> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), CarBody.class);
    }
}
