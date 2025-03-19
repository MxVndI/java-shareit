package ru.practicum.shareit.exception.booking;

public class BookingIsNotEndedException extends RuntimeException {
    public BookingIsNotEndedException(String message) {
        super(message);
    }
}
