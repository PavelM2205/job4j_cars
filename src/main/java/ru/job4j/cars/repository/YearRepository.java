package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Year;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class YearRepository {
    public static final Logger LOG = LoggerFactory.getLogger(YearRepository.class);
    private final static String FIND_ALL = "FROM Year";
    private final static String FIND_BY_ID = "FROM Year WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<Year> create(Year year) {
        Optional<Year> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(year));
            result = Optional.of(year);
        } catch (Exception exc) {
            LOG.error("Exception when add Year into DB: ", exc);
        }
        return result;
    }

    public List<Year> findAll() {
        return crudRepository.query(FIND_ALL, Year.class);
    }

    public Optional<Year> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Year.class);
    }

    public void update(Year year) {
        crudRepository.run(session -> session.update(year));
    }

    public void delete(int id) {
        Year year = new Year();
        year.setId(id);
        crudRepository.run(session -> session.remove(year));
    }
}
