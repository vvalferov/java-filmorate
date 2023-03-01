package ru.yandex.practicum.filmorate.services;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserService userService = new UserServiceImpl();
    User user = new User(1, "dudka@trubnik.ru", "login", "name", 1677690105L);
    User editedUser = new User(1, "newDudka@trubnik.ru", "login", "name", 1677690105L);

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