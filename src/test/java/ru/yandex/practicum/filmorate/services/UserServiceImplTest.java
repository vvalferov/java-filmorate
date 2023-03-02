package ru.yandex.practicum.filmorate.services;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserService userService = new UserServiceImpl();
    User user = new User(1L, "dudka@trubnik.ru", "login", "name", LocalDate.of(2011, 12, 11));
    User editedUser = new User(1L, "newDudka@trubnik.ru", "login", "name", LocalDate.of(2011, 12, 11));

    @Test
    void addUser() {
        assertEquals(userService.getAll().size(), 0);
        userService.addUser(user);
        assertEquals(userService.getAll().size(), 1);
    }

    @Test
    void editUser() {
        userService.addUser(user);
        assertEquals(userService.getAll().get(0).getEmail(), "dudka@trubnik.ru");
        userService.editUser(editedUser);
        assertEquals(userService.getAll().get(0).getEmail(), "newDudka@trubnik.ru");
    }

    @Test
    void getAll() {
        assertEquals(userService.getAll().size(), 0);
        userService.addUser(user);
        assertEquals(userService.getAll().size(), 1);
    }
}