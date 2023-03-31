package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;

    private final User user = User.builder()
            .id(1L)
            .email("dudka@trubnik.ru")
            .login("dudka")
            .name("trubnik")
            .birthday(LocalDate.now().minusDays(100))
            .build();
    private final User editedUser = User.builder()
            .id(1L)
            .email("trubnik@dudka.ru")
            .login("friend")
            .name("name")
            .birthday(LocalDate.now().minusDays(100))
            .build();
    private final User newUser = User.builder()
            .id(5L)
            .email("aa@bb.ru")
            .login("asdasd")
            .name("qwerty")
            .birthday(LocalDate.now().minusDays(100))
            .build();

    private final List<User> users = List.of(user);

    @Test
    void addUser() {
        assertEquals(userDbStorage.getAll().size(), 1);
        userDbStorage.addUser(newUser);
        assertEquals(userDbStorage.getAll().size(), 2);
    }

    @Test
    void editUser() {// +findUser
        userDbStorage.editUser(editedUser);
        assertEquals(editedUser, userDbStorage.findUser(user.getId()));
    }

    @Test
    void getAll() {
        userDbStorage.addUser(user);
        assertEquals(userDbStorage.getAll(), users);
    }
}