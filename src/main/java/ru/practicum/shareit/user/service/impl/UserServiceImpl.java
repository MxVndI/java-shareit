package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.model.mapper.UserMapper;
import ru.practicum.shareit.user.storage.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.validator.UserEmailValidationHandler;
import ru.practicum.shareit.user.validator.UserNameValidationHandler;
import ru.practicum.shareit.user.validator.UserValidationHandler;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userStorage;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        UserValidationHandler validationChain = createValidationChain();
        validationChain.handle(userDto, user, true);
        return UserMapper.toUserDto(userStorage.save(user));
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : userStorage.findAll()) {
            userDtos.add(UserMapper.toUserDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto findUserById(Integer id) {
        return UserMapper.toUserDto(userStorage.findById(id).get());
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = userStorage.findById(id).get();
        UserValidationHandler validationChain = createValidationChain();
        validationChain.handle(userDto, user, false);
        user.setId(id);
        return UserMapper.toUserDto(userStorage.save(user));
    }

    @Override
    public void deleteUserById(Integer id) {
        userStorage.deleteById(id);
    }

    private UserValidationHandler createValidationChain() {
        UserValidationHandler nameHandler = new UserNameValidationHandler();
        UserValidationHandler emailHandler = new UserEmailValidationHandler(userStorage);
        nameHandler.setNext(emailHandler);
        return nameHandler;
    }
}
