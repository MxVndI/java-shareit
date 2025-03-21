package ru.practicum.shareit.exception.user;

public class EmptyUserEmailException extends RuntimeException {
    public EmptyUserEmailException(String message) {
        super(message);
    }
}
