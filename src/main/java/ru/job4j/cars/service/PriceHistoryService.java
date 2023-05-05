package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistory create(PriceHistory priceHistory) {
        Optional<PriceHistory> optPriceHistory =
                priceHistoryRepository.create(priceHistory);
        if (optPriceHistory.isEmpty()) {
            throw new IllegalStateException("The PriceHistory has not added");
        }
        return optPriceHistory.get();
    }

    public List<PriceHistory> findAll() {
        return priceHistoryRepository.findAll();
    }

    public PriceHistory findById(int id) {
        Optional<PriceHistory> optPriceHistory = priceHistoryRepository.findById(id);
        if (optPriceHistory.isEmpty()) {
            throw new NoSuchElementException("PriceHistory is not found");
        }
        return optPriceHistory.get();
    }

    public void update(PriceHistory priceHistory) {
        priceHistoryRepository.update(priceHistory);
    }

    public void delete(int id) {
        priceHistoryRepository.delete(id);
    }
}
