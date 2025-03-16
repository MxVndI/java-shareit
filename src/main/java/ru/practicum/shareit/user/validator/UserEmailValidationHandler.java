package ru.practicum.shareit.user.validator;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.exception.user.FailedUserSaveException;
import ru.practicum.shareit.exception.user.InvalidEmailException;
import ru.practicum.shareit.exception.user.UserAlreadyExistException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

@RequiredArgsConstructor
public class UserEmailValidationHandler implements UserValidationHandler {
    private UserValidationHandler next;
    private final UserStorage userStorage;

    @Override
    public void setNext(UserValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(UserDto userDto, User user, boolean isCreating) {
        if (isCreating) {
            if (isUserEmailEmpty(userDto)) {
                throw new FailedUserSaveException("Почта пользователя обязательна для создания.");
            } else {
                user.setEmail(userDto.getEmail());
            }
        } else {
            if (!isUserEmailEmpty(userDto)) {
                user.setEmail(userDto.getEmail());
            }
        }
        checkUserEmailAvailable(user);
        if (next != null) {
            next.handle(userDto, user, isCreating);
        }
    }

    private void checkUserEmailAvailable(User user) {
        for (User u : userStorage.findAllUsers()) {
            if (u.getEmail().equals(user.getEmail()) && !u.getId().equals(user.getId())) {
                throw new UserAlreadyExistException("Email " + user.getEmail() + " уже зарегистрирован");
            }
        }
        if (!EmailValidator.isValidEmail(user.getEmail())) {
            throw new InvalidEmailException("Неправильно указана почта");
        }
    }

    private boolean isUserEmailEmpty(UserDto userDto) {
        return userDto.getEmail() == null || userDto.getEmail().isEmpty();
    }
}