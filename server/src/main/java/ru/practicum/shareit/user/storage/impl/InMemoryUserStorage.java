package ru.practicum.shareit.user.storage.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Repository
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();

    public User save(User user) {
        user.setId(getLastId());
        this.users.put(user.getId(), user);
        return user;
    }

    private Integer getLastId() {
        if (users.isEmpty())
            return 1;
        List<User> userList = new ArrayList<>(users.values());
        User lastUser = userList.getLast();
        return lastUser != null ? lastUser.getId() + 1 : 1;
    }


    public User updateUser(User user) {
        this.users.replace(user.getId(), user);
        return user;
    }

    public void deleteUserById(Integer id) {
        users.remove(id);
    }

    public User findUserById(Integer id) {
        Optional<User> user = Optional.ofNullable(users.get(id));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден.");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return users.values().stream().toList();
    }

}
