package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("Entered GET user");
        return ResponseEntity.ok().body(userService.userStorage.getUser(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        log.info("Entered POST user");
        return ResponseEntity.ok().body(userService.userStorage.addUser(user));
    }

    @PutMapping
    public ResponseEntity<User> editUser(@Valid @RequestBody User user) {
        log.info("Entered PUT user");
        return ResponseEntity.ok().body(userService.userStorage.editUser(user));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Boolean> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Entered PUT user");
        return ResponseEntity.ok(userService.addFriend(id, friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Boolean> removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Entered DELETE user");
        return ResponseEntity.ok(userService.removeFriend(id, friendId));
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getAllFriends(@PathVariable Long id) {
        log.info("Entered GET user");
        return ResponseEntity.ok(userService.getAllFriends(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Entered GET user");
        return ResponseEntity.ok(userService.getCommonFriends(id, otherId));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Entered GET user");
        return ResponseEntity.ok().body(userService.userStorage.getAll());
    }
}
