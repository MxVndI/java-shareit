package ru.practicum.shareit.exception.item;

public class InvalidItemDataException extends RuntimeException {
    public InvalidItemDataException(String message) {
        super(message);
    }
}
