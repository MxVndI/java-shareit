package ru.practicum.shareit.user.storage.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.user.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.user.validator.EmailValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryUserStorage implements UserStorage {
    private final LinkedList<User> users = new LinkedList<>();

    public User save(User user) {
        checkUserEmailAvailable(user);
        user.setId(getLastId());
        if (!isUserNameEmpty(user) && !isUserEmailEmpty(user)) {
            this.users.add(user);
            return user;
        } else {
            throw new FailedUserSaveException("Не удалось сохранить пользователя");
        }
    }

    private Integer getLastId() {
        if (users.isEmpty())
            return 1;
        return users.getLast().getId() + 1;
    }

    public User updateUser(User user, Integer userId) {
        User existingUser = findUserById(userId);
        if (existingUser == null) {
            throw new UserNotFoundException("Пользователь с ID " + userId + " не найден");
        }
        if (!isUserNameEmpty(user)) {
            existingUser.setName(user.getName());
        }
        if (!isUserEmailEmpty(user)) {
            checkUserEmailAvailable(user);
            existingUser.setEmail(user.getEmail());
        }
        int index = this.users.indexOf(existingUser);
        this.users.set(index, existingUser);
        return existingUser;
    }

    public User deleteUserById(Integer id) {
        User user = findUserById(id);
        users.remove(user);
        return user;
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

    private void checkUserEmailAvailable(User user) {
        for (User u : this.users) {
            if (u.getEmail().equals(user.getEmail()) && !u.getId().equals(user.getId())) {
                throw new UserAlreadyExistException("Email " + user.getEmail() + " уже зарегистрирован");
            }
        }
        if (!EmailValidator.isValidEmail(user.getEmail())) {
            throw new InvalidEmailException("Неправильно указана почта");
        }
    }

    private boolean isUserNameEmpty(User user) {
        return user.getName() == null || user.getName().isEmpty();
    }

    private boolean isUserEmailEmpty(User user) {
        return user.getEmail() == null || user.getEmail().isEmpty();
    }

}
