package ru.practicum.shareit.exception.request;

public class ItemRequestNotFound extends RuntimeException {
    public ItemRequestNotFound(String message) {
        super(message);
    }
}
