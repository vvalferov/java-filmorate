package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable Long id) {
        log.info("Entered GET user");
        return ResponseEntity.ok().body(userService.userStorage.findUser(id));
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> handleNotExistingUser(final UserNotExistException e) {
        return Map.of("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Map<String, String> handleNotValidUser(final UserNotValidException e) {
        return Map.of("error", e.getMessage());
    }
}
