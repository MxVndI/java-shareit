package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {

    User createUser(UserDto userDto);

    List<User> findAllUsers();

    User findUserById(Integer id);

    User updateUser(Integer id, UserDto user);

    User deleteUserById(Integer id);
}
