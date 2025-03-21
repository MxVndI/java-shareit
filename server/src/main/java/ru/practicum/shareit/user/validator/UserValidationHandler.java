package ru.practicum.shareit.user.validator;

import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

public interface UserValidationHandler {
    void setNext(UserValidationHandler next);

    void handle(UserDto userDto, User user, boolean isCreating) throws UserNotFoundException;
}
