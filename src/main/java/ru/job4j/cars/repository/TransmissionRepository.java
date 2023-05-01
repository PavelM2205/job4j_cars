package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TransmissionRepository {
    private final static String FIND_ALL = "FROM Transmission";
    private final static String FIND_BY_ID = "From Transmission WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<Transmission> findAll() {
        return crudRepository.query(FIND_ALL, Transmission.class);
    }

    public Optional<Transmission> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Transmission.class);
    }
}
