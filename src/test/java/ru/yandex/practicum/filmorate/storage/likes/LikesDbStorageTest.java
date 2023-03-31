package ru.yandex.practicum.filmorate.storage.likes;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LikesDbStorageTest {
    private final UserDbStorage userStorage;
    private final LikesDbStorage likesStorage;
    private final FilmDbStorage filmStorage;

    private void setDbs() {
        userStorage.addUser(User.builder()
                .id(1L)
                .email("dudka@trubnik.ru")
                .login("dudka")
                .name("trubnik")
                .birthday(LocalDate.now().minusDays(100))
                .build());
        userStorage.addUser(User.builder()
                .id(2L)
                .email("trubnik@dudka.ru")
                .login("friend")
                .name("name")
                .birthday(LocalDate.now().minusDays(100))
                .build());
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);
    }

    private final Film film1 = Film.builder()
            .id(1L)
            .name("Name")
            .description("Description")
            .releaseDate(LocalDate.now())
            .duration(100)
            .mpa(
                    Mpa.builder()
                            .id(1L)
                            .name("G")
                            .build()
            )
            .genres(new LinkedHashSet<>())
            .build();
    private final Film film2 = Film.builder()
            .id(2L)
            .name("New")
            .description("New description")
            .releaseDate(LocalDate.now())
            .duration(150)
            .mpa(
                    Mpa.builder()
                            .id(2L)
                            .name("PG")
                            .build()
            )
            .genres(new LinkedHashSet<>())
            .build();

    @Test
    void addLike() { //+getLike
        setDbs();
        assertEquals(likesStorage.getLike(1L).size(), 0);
        likesStorage.addLike(1L, 1L);
        assertEquals(likesStorage.getLike(1L).size(), 1);
    }

    @Test
    void removeLike() {
        assertEquals(likesStorage.getLike(1L).size(), 1);
        likesStorage.removeLike(1L, 1L);
        assertEquals(likesStorage.getLike(1L).size(), 0);
    }

    @Test
    void getMostPopularFilms() {
        likesStorage.addLike(1L, 1L);
        assertEquals(likesStorage.getMostPopularFilms(1).get(0), film1);
        likesStorage.addLike(2L, 1L);
        likesStorage.addLike(2L, 2L);
        assertEquals(likesStorage.getMostPopularFilms(1).get(0), film2);
    }
}