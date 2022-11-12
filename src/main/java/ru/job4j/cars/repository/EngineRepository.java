package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {
    private static final Logger LOG = LoggerFactory.getLogger(EngineRepository.class);
    private static final String FIND_ALL = "FROM Engine";
    private static final String FIND_BY_ID = "FROM Engine WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<Engine> create(Engine engine) {
        Optional<Engine> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(engine));
            result = Optional.of(engine);
        } catch (Exception exc) {
            LOG.error("Exception when create Engine: ", exc);
        }
        return result;
    }

    public void update(Engine engine) {
        crudRepository.run(session -> session.update(engine));
    }

    public void delete(int id) {
        Engine engine = new Engine();
        engine.setId(id);
        crudRepository.run(session -> session.remove(engine));
    }

    public List<Engine> findAll() {
        return crudRepository.query(FIND_ALL, Engine.class);
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Engine.class);
    }
}
