package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.WheelDrive;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class WheelDriveRepository {
    public static final Logger LOG = LoggerFactory.getLogger(WheelDriveRepository.class);
    private final static String FIND_ALL = "FROM WheelDrive";
    private final static String FIND_BY_ID = "FROM WheelDrive WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<WheelDrive> create(WheelDrive wheelDrive) {
        Optional<WheelDrive> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(wheelDrive));
            result = Optional.of(wheelDrive);
        } catch (Exception exc) {
            LOG.error("Exception when add WheelDrive into DB: ", exc);
        }
        return result;
    }

    public List<WheelDrive> findAll() {
        return crudRepository.query(FIND_ALL, WheelDrive.class);
    }

    public Optional<WheelDrive> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id),
                WheelDrive.class);
    }

    public void update(WheelDrive wheelDrive) {
        crudRepository.run(session -> session.update(wheelDrive));
    }

    public void delete(int id) {
        WheelDrive wheelDrive = new WheelDrive();
        wheelDrive.setId(id);
        crudRepository.run(session -> session.remove(wheelDrive));
    }
}
