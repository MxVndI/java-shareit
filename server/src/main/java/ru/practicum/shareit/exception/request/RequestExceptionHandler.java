package ru.practicum.shareit.exception.request;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.shareit.exception.ErrorResponse;

@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(ItemRequestNotFound.class)
    public ResponseEntity<ErrorResponse> handleItemRequestNotFound(ItemRequestNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
