package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DriverRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DriverRepository.class);
    private static final String FIND_ALL = "FROM Driver";
    private static final String FIND_BY_ID = "FROM Driver WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<Driver> create(Driver driver) {
        Optional<Driver> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(driver));
            result = Optional.of(driver);
        } catch (Exception exc) {
            LOG.error("Exception when create Driver: ", exc);
        }
        return result;
    }

    public void update(Driver driver) {
        crudRepository.run(session -> session.update(driver));
    }

    public void delete(int id) {
        Driver driver = new Driver();
        driver.setId(id);
        crudRepository.run(session -> session.remove(driver));
    }

    public List<Driver> findAll() {
        return crudRepository.query(FIND_ALL, Driver.class);
    }

    public Optional<Driver> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Driver.class);
    }
}
