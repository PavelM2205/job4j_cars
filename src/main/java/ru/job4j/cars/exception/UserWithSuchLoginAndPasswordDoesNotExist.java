package ru.job4j.cars.exception;

public class UserWithSuchLoginAndPasswordDoesNotExist extends RuntimeException {
    public UserWithSuchLoginAndPasswordDoesNotExist(String message) {
        super(message);
    }
}
