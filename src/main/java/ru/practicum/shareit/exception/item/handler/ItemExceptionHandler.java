package ru.practicum.shareit.exception.item.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.shareit.exception.ErrorResponse;
import ru.practicum.shareit.exception.item.*;

@ControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UncorrectOwnerException.class)
    public ResponseEntity<ErrorResponse> handleUncorrectOwnerException(UncorrectOwnerException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(FailedItemSaveException.class)
    public ResponseEntity<ItemErrorResponse> handleFailedItemSaveException(FailedItemSaveException ex) {
        ItemErrorResponse errorResponse = new ItemErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidItemDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidItemDataException(InvalidItemDataException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
