package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {
    private final GenreDbStorage genreDbStorage;
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

    private final Genre genre1 = Genre.builder()
            .id(1L)
            .name("Комедия")
            .build();

    private final Genre genre2 = Genre.builder()
            .id(2L)
            .name("Драма")
            .build();

    private final Genre genre3 = Genre.builder()
            .id(3L)
            .name("Мультфильм")
            .build();

    private final Genre genre4 = Genre.builder()
            .id(4L)
            .name("Триллер")
            .build();

    private final Genre genre5 = Genre.builder()
            .id(5L)
            .name("Документальный")
            .build();

    private final Genre genre6 = Genre.builder()
            .id(6L)
            .name("Боевик")
            .build();

    private final List<Genre> genres = List.of(genre1, genre2, genre3, genre4, genre5, genre6);

    @Test
    void getGenre() {
        assertEquals(genreDbStorage.getGenre(1L), genreDbStorage.getGenre(1L));
    }

    @Test
    void getAll() {
        filmDbStorage.addFilm(film);
        assertEquals(genres, genreDbStorage.getAll());
    }

    @Test
    void getGenres() {
        genreDbStorage.setGenres(film.getId(), Set.of(genreDbStorage.getGenre(1L), genreDbStorage.getGenre(2L)));
        film.setGenres(Set.of(genreDbStorage.getGenre(1L), genreDbStorage.getGenre(2L)));
        assertEquals(Set.of(genreDbStorage.getGenre(1L), genreDbStorage.getGenre(2L)), genreDbStorage.getGenres(film.getId()));
    }

    @Test
    void getGenresAsMap() {
        Map<Long, Set<Genre>> map = new HashMap<>();
        map.put(1L, Set.of(genreDbStorage.getGenre(1L), genreDbStorage.getGenre(2L), genreDbStorage.getGenre(3L)));
        genreDbStorage.setGenres(film.getId(), map.get(1L));
        assertEquals(map, genreDbStorage.getGenresAsMap(List.of(film)));
    }

    @Test
    void setGenres() {
        assertEquals(genreDbStorage.getGenres(film.getId()).size(), 3);
        genreDbStorage.setGenres(film.getId(), Set.of(genreDbStorage.getGenre(4L)));
        assertEquals(genreDbStorage.getGenres(film.getId()).size(), 1);
    }

    @Test
    void removeGenres() {
        genreDbStorage.setGenres(1L, Set.of(genre1));
        assertEquals(genreDbStorage.getGenres(film.getId()).size(), 1);
        genreDbStorage.removeGenres(film.getId());
        assertEquals(genreDbStorage.getGenres(film.getId()).size(), 0);
    }
}