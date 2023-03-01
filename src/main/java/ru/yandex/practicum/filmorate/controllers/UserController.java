package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;
import ru.yandex.practicum.filmorate.services.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserServiceImpl();

    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.addUser(user));
    }

    @PutMapping(value = "/edit", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> editUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.editUser(user));
    }

    @GetMapping(value = "/get_all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }
}
