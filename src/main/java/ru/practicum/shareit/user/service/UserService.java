package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> findAllUsers();

    UserDto findUserById(Integer id);

    UserDto updateUser(Integer id, UserDto user);

    void deleteUserById(Integer id);
}
