package ru.job4j.cars.exception;

public class UserWithSuchLoginAlreadyExists extends RuntimeException {
    public UserWithSuchLoginAlreadyExists(String message) {
        super(message);
    }
}
