package ru.practicum.shareit.user.validator;

import ru.practicum.shareit.exception.user.FailedUserSaveException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

public class UserNameValidationHandler implements UserValidationHandler {
    private UserValidationHandler next;

    @Override
    public void setNext(UserValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(UserDto userDto, User user, boolean isCreating) {
        if (isCreating) {
            if (isUserNameEmpty(userDto)) {
                throw new FailedUserSaveException("Имя пользователя обязательно для создания.");
            } else {
                user.setName(userDto.getName());
            }
        } else {
            if (!isUserNameEmpty(userDto)) {
                user.setName(userDto.getName());
            }
        }
        if (next != null) {
            next.handle(userDto, user, isCreating);
        }
    }

    private boolean isUserNameEmpty(UserDto userDto) {
        return userDto.getName() == null || userDto.getName().isEmpty();
    }

}