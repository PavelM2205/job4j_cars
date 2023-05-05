package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Transmission;
import ru.job4j.cars.repository.TransmissionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransmissionService {
    private final TransmissionRepository transmissionRepository;

    public Transmission create(Transmission transmission) {
        Optional<Transmission> optTransmission = transmissionRepository
                .create(transmission);
        if (optTransmission.isEmpty()) {
            throw new IllegalStateException("The Transmission has not added");
        }
        return optTransmission.get();
    }


        public List<Transmission> findAll() {
        return transmissionRepository.findAll();
    }

    public Transmission findById(int id) {
        Optional<Transmission> optionalTransmission = transmissionRepository.findById(id);
        if (optionalTransmission.isEmpty()) {
            throw new NoSuchElementException("Transmission is not found");
        }
        return optionalTransmission.get();
    }

    public void update(Transmission transmission) {
        transmissionRepository.update(transmission);
    }

    public void delete(int id) {
        transmissionRepository.delete(id);
    }
}
