package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.exception.UserWithSuchLoginAlreadyExists;
import ru.job4j.cars.exception.UserWithSuchLoginAndPasswordDoesNotExist;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        Optional<User> optUser = userRepository.create(user);
        if (optUser.isEmpty()) {
            throw new UserWithSuchLoginAlreadyExists(
                    "User with such login already exists");
        }
        return optUser.get();
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(int id) {
        userRepository.delete(id);
    }

    public List<User> findAllOrderById() {
        return userRepository.findAllOrderById();
    }

    public User findById(int id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new NoSuchElementException("User is not found");
        }
        return optUser.get();
    }

    public List<User> findByLikeLogin(String key) {
        return userRepository.findByLikeLogin(key);
    }

    public User findByLogin(String login) {
        Optional<User> optUser = userRepository.findByLogin(login);
        if (optUser.isEmpty()) {
            throw new NoSuchElementException("User is not found");
        }
        return optUser.get();
    }

    public User findByLoginAndPassword(String login, String password) {
        Optional<User> optUser = userRepository.findByLoginAndPassword(login, password);
        if (optUser.isEmpty()) {
            throw new UserWithSuchLoginAndPasswordDoesNotExist(
                    "User with such login and password does not exist");
        }
        return optUser.get();
    }
}
