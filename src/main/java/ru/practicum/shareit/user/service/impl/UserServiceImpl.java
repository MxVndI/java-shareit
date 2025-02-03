package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return userStorage.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    @Override
    public User findUserById(Integer id) {
        return userStorage.findUserById(id);
    }

    @Override
    public User updateUser(Integer id, UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setId(id);
        return userStorage.updateUser(user, id);
    }

    @Override
    public User deleteUserById(Integer id) {
        return userStorage.deleteUserById(id);
    }

}
