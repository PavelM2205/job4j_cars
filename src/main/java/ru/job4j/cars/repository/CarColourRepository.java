package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarColour;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarColourRepository {
    public static final Logger LOG = LoggerFactory.getLogger(CarColourRepository.class);
    private final static String FIND_ALL = "FROM CarColour";
    private final static String FIND_BY_ID = "FROM CarColour WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<CarColour> create(CarColour carColour) {
        Optional<CarColour> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(carColour));
            result = Optional.of(carColour);
        } catch (Exception exc) {
            LOG.error("Exception when add CarColour into DB: ", exc);
        }
        return result;
    }

    public List<CarColour> findAll() {
        return crudRepository.query(FIND_ALL, CarColour.class);
    }

    public Optional<CarColour> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), CarColour.class);
    }

    public void update(CarColour carColour) {
        crudRepository.run(session -> session.update(carColour));
    }

    public void delete(int id) {
        CarColour carColour = new CarColour();
        carColour.setId(id);
        crudRepository.run(session -> session.remove(carColour));
    }
}
