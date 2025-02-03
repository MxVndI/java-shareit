package ru.practicum.shareit.exception.user;

public class FailedUserSaveException extends RuntimeException {
    public FailedUserSaveException(String message) {
        super(message);
    }
}
