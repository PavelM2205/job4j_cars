package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;

    public Engine create(Engine engine) {
        Optional<Engine> optEngine = engineRepository.create(engine);
        if (optEngine.isEmpty()) {
            throw new IllegalStateException("The engine has not added");
        }
        return optEngine.get();
    }

    public void update(Engine engine) {
        engineRepository.update(engine);
    }

    public void delete(int id) {
        engineRepository.delete(id);
    }

    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    public Engine findById(int id) {
        Optional<Engine> optEngine = engineRepository.findById(id);
        if (optEngine.isEmpty()) {
            throw new NoSuchElementException("Engine is not found");
        }
        return optEngine.get();
    }
}
