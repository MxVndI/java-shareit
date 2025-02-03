package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable("id") Integer id, @RequestBody @Valid UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public User deleteUserById(@PathVariable("id") Integer id) {
        return userService.deleteUserById(id);
    }
}
