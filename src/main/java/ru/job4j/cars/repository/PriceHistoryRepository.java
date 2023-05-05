package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriceHistoryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PriceHistoryRepository.class);
    private static final String FIND_ALL = "From PriceHistory";
    public static final String FIND_BY_ID = "FROM PriceHistory WHERE id = :fId";
    private final CrudRepository crudRepository;

    public Optional<PriceHistory> create(PriceHistory priceHistory) {
        Optional<PriceHistory> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(priceHistory));
            result = Optional.of(priceHistory);
        } catch (Exception exc) {
            LOG.error("Exception when create PriceHistory: ", exc);
        }
        return result;
    }

    public List<PriceHistory> findAll() {
        return crudRepository.query(FIND_ALL, PriceHistory.class);
    }

    public Optional<PriceHistory> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id),
                PriceHistory.class);
    }

    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.update(priceHistory));
    }

    public void delete(int id) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setId(id);
        crudRepository.run(session -> session.remove(priceHistory));
    }
}
