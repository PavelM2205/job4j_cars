package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Year;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class YearRepository {
    private final static String FIND_ALL = "FROM Year";
    private final static String FIND_BY_ID = "FROM Year WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<Year> findAll() {
        return crudRepository.query(FIND_ALL, Year.class);
    }

    public Optional<Year> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Year.class);
    }
}
