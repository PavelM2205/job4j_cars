package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarBrandRepository {
    private static final String FIND_ALL = "FROM CarBrand";
    private static final String FIND_BY_ID = "FROM CarBrand WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<CarBrand> findAll() {
        return crudRepository.query(FIND_ALL, CarBrand.class);
    }

    public Optional<CarBrand> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), CarBrand.class);
    }
}
