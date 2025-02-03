package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User save(User user);

    User updateUser(User user, Integer userId);

    User deleteUserById(Integer id);

    User findUserById(Integer id);

    List<User> findAllUsers();
}
