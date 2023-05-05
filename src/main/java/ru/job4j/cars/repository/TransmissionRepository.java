package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TransmissionRepository {
    public static final Logger LOG = LoggerFactory.getLogger(TransmissionRepository.class);
    private final static String FIND_ALL = "FROM Transmission";
    private final static String FIND_BY_ID = "From Transmission WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<Transmission> create(Transmission transmission) {
        Optional<Transmission> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(transmission));
            result = Optional.of(transmission);
        } catch (Exception exc) {
            LOG.error("Exception when add Transmission into DB: ", exc);
        }
        return result;
    }

    public List<Transmission> findAll() {
        return crudRepository.query(FIND_ALL, Transmission.class);
    }

    public Optional<Transmission> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Transmission.class);
    }

    public void update(Transmission transmission) {
        crudRepository.run(session -> session.update(transmission));
    }

    public void delete(int id) {
        Transmission transmission = new Transmission();
        transmission.setId(id);
        crudRepository.run(session -> session.remove(transmission));
    }
}
