package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.WheelDrive;
import ru.job4j.cars.repository.WheelDriveRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WheelDriveService {
    private final WheelDriveRepository wheelDriveRepository;

    public List<WheelDrive> findAll() {
        return wheelDriveRepository.findAll();
    }

    public WheelDrive findById(int id) {
        Optional<WheelDrive> optWheelDrive = wheelDriveRepository.findById(id);
        if (optWheelDrive.isEmpty()) {
            throw new NoSuchElementException("WheelDrive is not found");
        }
        return optWheelDrive.get();
    }
}
