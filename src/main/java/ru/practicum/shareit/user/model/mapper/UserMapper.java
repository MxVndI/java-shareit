package ru.practicum.shareit.user.model.mapper;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail()
        );
    }
}
