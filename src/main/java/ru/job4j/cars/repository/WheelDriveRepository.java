package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.WheelDrive;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class WheelDriveRepository {
    private final static String FIND_ALL = "FROM WheelDrive";
    private final static String FIND_BY_ID = "FROM WheelDrive WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<WheelDrive> findAll() {
        return crudRepository.query(FIND_ALL, WheelDrive.class);
    }

    public Optional<WheelDrive> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id),
                WheelDrive.class);
    }
}
