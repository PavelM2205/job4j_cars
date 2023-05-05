package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarBodyRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CarBodyRepository.class);
    private final static String FIND_ALL = "FROM CarBody";
    private final static String FIND_BY_ID = "FROM CarBody WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<CarBody> create(CarBody carBody) {
        Optional<CarBody> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(carBody));
            result = Optional.of(carBody);
        } catch (Exception exc) {
            LOG.error("Exception when add CarBody into DB: ", exc);
        }
        return result;
    }

    public List<CarBody> findAll() {
        return crudRepository.query(FIND_ALL, CarBody.class);
    }

    public Optional<CarBody> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), CarBody.class);
    }

    public void delete(int id) {
        CarBody carBody = new CarBody();
        carBody.setId(id);
        crudRepository.run(session -> session.remove(carBody));
    }

    public void update(CarBody carBody) {
        crudRepository.run(session -> session.update(carBody));
    }
}
