package ru.practicum.shareit.exception.item;

public class FailedItemSaveException extends RuntimeException {
    public FailedItemSaveException(String message) {
        super(message);
    }
}
