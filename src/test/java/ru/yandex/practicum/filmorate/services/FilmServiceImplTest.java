package ru.yandex.practicum.filmorate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceImplTest {
    FilmService filmService = new FilmServiceImpl();
    Film film = new Film(1, "TestFilm", "TestDescription", LocalDate.of(2021, 12, 11), 70);
    Film editedFilm = new Film(1, "TestFilm", "editedDescription", LocalDate.of(2021, 12, 11), 70);

    @Test
    void addFilm() {
        assertEquals(filmService.getAll().size(), 0);
        filmService.addFilm(film);
        assertEquals(filmService.getAll().size(), 1);
    }

    @Test
    void editFilm() {
        filmService.addFilm(film);
        assertEquals(filmService.getAll().get(0).getDescription(), "TestDescription");
        filmService.editFilm(editedFilm);
        assertEquals(filmService.getAll().get(0).getDescription(), "editedDescription");
    }

    @Test
    void getAll() {
        assertEquals(filmService.getAll().size(), 0);
        filmService.addFilm(film);
        assertEquals(filmService.getAll().size(), 1);
    }
}