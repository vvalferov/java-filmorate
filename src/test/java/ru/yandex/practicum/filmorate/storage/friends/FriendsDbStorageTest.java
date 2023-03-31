package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FriendsDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final FriendsDbStorage friendsDbStorage;
    private final User user = User.builder()
            .id(1L)
            .email("dudka@trubnik.ru")
            .login("dudka")
            .name("trubnik")
            .birthday(LocalDate.now().minusDays(100))
            .build();
    private final User friend = User.builder()
            .id(2L)
            .email("trubnik@dudka.ru")
            .login("friend")
            .name("name")
            .birthday(LocalDate.now().minusDays(100))
            .build();
    private final User someoneElse = User.builder()
            .id(3L)
            .email("dude@duude.ru")
            .login("else")
            .name("Ivan")
            .birthday(LocalDate.now().minusDays(1000))
            .build();

    void addUsersToDb() {
        userDbStorage.addUser(user);
        userDbStorage.addUser(friend);
        userDbStorage.addUser(someoneElse);

        friendsDbStorage.addFriend(1L, 2L);
    }

    @Test
    void findFriend() {
        assertEquals(friend, friendsDbStorage.findFriend(1L).get(0));
    }

    @Test
    void addFriend() {
        friendsDbStorage.addFriend(1L, 3L);
        assertEquals(someoneElse, friendsDbStorage.findFriend(1L).get(1));
    }

    @Test
    void findMutual() {
        friendsDbStorage.addFriend(2L, 3L);
        assertEquals(List.of(someoneElse), friendsDbStorage.findMutual(1L, 2L));
    }

    @Test
    void removeFriend() {
        addUsersToDb();
        friendsDbStorage.removeFriend(1L, 3L);
        assertEquals(List.of(friend), friendsDbStorage.findFriend(1L));
    }
}