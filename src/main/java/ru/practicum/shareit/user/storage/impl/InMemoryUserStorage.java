package ru.practicum.shareit.user.storage.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryUserStorage implements UserStorage {
    private final LinkedList<User> users = new LinkedList<>();

    public User save(User user) {
        user.setId(getLastId());
        this.users.add(user);
        return user;
    }

    private Integer getLastId() {
        if (users.isEmpty())
            return 1;
        return users.getLast().getId() + 1;
    }

    public User updateUser(User user) {
        this.users.set(user.getId() - 1, user);
        return user;
    }

    public void deleteUserById(Integer id) {
        User user = findUserById(id);
        users.remove(user);
    }

    public User findUserById(Integer id) {
        Optional<User> user = users.stream().filter(u -> Objects.equals(u.getId(), id)).findFirst();
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден.");
        }
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

}
