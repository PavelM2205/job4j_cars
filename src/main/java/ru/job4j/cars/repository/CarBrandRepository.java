package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarBrandRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CarBrandRepository.class);
    private static final String FIND_ALL = "FROM CarBrand";
    private static final String FIND_BY_ID = "FROM CarBrand WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<CarBrand> create(CarBrand carBrand) {
        Optional<CarBrand> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(carBrand));
            result = Optional.of(carBrand);
        } catch (Exception exc) {
            LOG.error("Exception when add CarBrand into DB: ", exc);
        }
        return result;
    }

    public List<CarBrand> findAll() {
        return crudRepository.query(FIND_ALL, CarBrand.class);
    }

    public Optional<CarBrand> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), CarBrand.class);
    }

    public void update(CarBrand carBrand) {
        crudRepository.run(session -> session.update(carBrand));
    }

    public void delete(int id) {
        CarBrand carBrand = new CarBrand();
        carBrand.setId(id);
        crudRepository.run(session -> session.remove(carBrand));
    }
}
