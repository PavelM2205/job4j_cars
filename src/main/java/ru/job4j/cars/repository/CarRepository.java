package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class);
    private static final String FIND_ALL = "FROM Car";
    private static final String FIND_BY_ID = "FROM Car WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<Car> create(Car car) {
        Optional<Car> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(car));
            result = Optional.of(car);
        } catch (Exception exc) {
            LOG.error("Exception when create Car: ", exc);
        }
        return result;
    }

    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }

    public void delete(int id) {
        Car car = new Car();
        car.setId(id);
        crudRepository.run(session -> session.remove(car));
    }

    public List<Car> findAll() {
        return crudRepository.query(FIND_ALL, Car.class);
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Car.class);
    }
}
