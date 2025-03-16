package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.model.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.user.validator.UserEmailValidationHandler;
import ru.practicum.shareit.user.validator.UserNameValidationHandler;
import ru.practicum.shareit.user.validator.UserValidationHandler;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        UserValidationHandler validationChain = createValidationChain();
        validationChain.handle(userDto, user, true);
        return UserMapper.toUserDto(userStorage.save(user));
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : userStorage.findAllUsers()) {
            userDtos.add(UserMapper.toUserDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto findUserById(Integer id) {
        return UserMapper.toUserDto(userStorage.findUserById(id));
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = userStorage.findUserById(id);
        UserValidationHandler validationChain = createValidationChain();
        validationChain.handle(userDto, user, false);
        user.setId(id);
        return UserMapper.toUserDto(userStorage.updateUser(user));
    }

    @Override
    public void deleteUserById(Integer id) {
        userStorage.deleteUserById(id);
    }

    private UserValidationHandler createValidationChain() {
        UserValidationHandler nameHandler = new UserNameValidationHandler();
        UserValidationHandler emailHandler = new UserEmailValidationHandler(userStorage);
        nameHandler.setNext(emailHandler);
        return nameHandler;
    }
}
