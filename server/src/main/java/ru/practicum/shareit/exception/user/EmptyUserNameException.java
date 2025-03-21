package ru.practicum.shareit.exception.user;

public class EmptyUserNameException extends RuntimeException {
    public EmptyUserNameException(String message) {
        super(message);
    }
}
