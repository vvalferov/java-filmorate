package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final Film film = Film.builder()
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
    private final Film editedFilm = Film.builder()
            .id(film.getId())
            .name("New name")
            .description("New description")
            .releaseDate(LocalDate.now())
            .duration(200)
            .mpa(
                    Mpa.builder()
                            .id(2L)
                            .name("PG")
                            .build()
            )
            .genres(new LinkedHashSet<>())
            .build();
    private final Film oneMoreFilm = Film.builder()
            .id(555L)
            .name("Dudka")
            .description("Trubnik")
            .releaseDate(LocalDate.now())
            .duration(500)
            .mpa(
                    Mpa.builder()
                            .id(3L)
                            .name("PG-13")
                            .build()
            )
            .genres(new LinkedHashSet<>())
            .build();

    @Test
    void addFilm() {
        assertEquals(film, filmDbStorage.findFilm(film.getId()));
    }

    @Test
    void editFilm() {
        filmDbStorage.editFilm(editedFilm);
        assertEquals(editedFilm, filmDbStorage.findFilm(editedFilm.getId()));
    }

    @Test
    void getAll() {
        filmDbStorage.addFilm(film);
        filmDbStorage.addFilm(oneMoreFilm);
        assertEquals(2, filmDbStorage.getAll().size());
    }

    @Test
    void findFilm() {
        assertEquals(film, filmDbStorage.findFilm(1L));
    }
}