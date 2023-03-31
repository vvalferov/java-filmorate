package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;
    private final FilmDbStorage filmDbStorage;

    private final List<Mpa> mpaList = List.of(
            Mpa.builder().name("G").id(1L).build(),
            Mpa.builder().name("PG").id(2L).build(),
            Mpa.builder().name("PG-13").id(3L).build(),
            Mpa.builder().name("R").id(4L).build(),
            Mpa.builder().name("NC-17").id(5L).build());
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

    @Test
    void getMpa() {
        filmDbStorage.addFilm(film);
        assertEquals(mpaDbStorage.getMpa(1L), film.getMpa());
    }

    @Test
    void getAll() {
        assertEquals(mpaDbStorage.getAll(), mpaList);
    }
}