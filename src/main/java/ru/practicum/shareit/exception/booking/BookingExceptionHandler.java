package ru.practicum.shareit.exception.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.shareit.exception.ErrorResponse;

@ControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler(ItemNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleItemNotAvailableException(ItemNotAvailableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotValidDateException.class)
    public ResponseEntity<ErrorResponse> handleItemNotAvailableException(NotValidDateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BookingIsNotEndedException.class)
    public ResponseEntity<ErrorResponse> handleBookingIsNotEnded(BookingIsNotEndedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
