package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.City;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CityRepository {
    public static final Logger LOG = LoggerFactory.getLogger(CityRepository.class);
    private final static String FIND_ALL = "From City";
    private final static String FIND_BY_ID = "FROM City WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<City> create(City city) {
        Optional<City> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(city));
            result = Optional.of(city);
        } catch (Exception exc) {
            LOG.error("Exception when add City into DB: ", exc);
        }
        return result;
    }

    public List<City> findAll() {
        return crudRepository.query(FIND_ALL, City.class);
    }

    public Optional<City> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), City.class);
    }

    public void update(City city) {
        crudRepository.run(session -> session.update(city));
    }

    public void delete(int id) {
        City city = new City();
        city.setId(id);
        crudRepository.run(session -> session.remove(city));
    }
}
