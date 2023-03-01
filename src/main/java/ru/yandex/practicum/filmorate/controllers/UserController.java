package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.addUser(user));
    }

    @PutMapping
    public ResponseEntity<User> editUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.editUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }
}
