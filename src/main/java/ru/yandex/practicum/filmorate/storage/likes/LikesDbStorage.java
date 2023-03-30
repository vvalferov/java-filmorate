package ru.yandex.practicum.filmorate.storage.likes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.utils.Mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    @Override
    public List<Long> get(Long filmId) {
        String sqlQuery = "SELECT USER_ID " +
            "FROM LIKES " +
            "WHERE FILM_ID = ?";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }

    @Override
    public void add(Long filmId, Long userId) {
        String sqlQuery = "insert into LIKES(film_id, user_id) " +
            "values ( ?, ? )";

        try {
            jdbcTemplate.update(sqlQuery, filmId, userId);
            log.info("User {} has liked film {}", userId, filmId);
        } catch (DuplicateKeyException ignored) {
            log.warn("User {} has already liked film {}", userId, filmId);
        }
    }

    @Override
    public void remove(Long filmId, Long userId) {
        String sqlQuery = "delete from LIKES " +
            "where FILM_ID = ? and USER_ID = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        List<Film> films = new LinkedList<>();
        String sqlQuery = "SELECT F.*, M.MPA_NAME " +
            "FROM FILMS AS F " +
            "LEFT JOIN LIKES L on F.FILM_ID = L.FILM_ID " +
            "LEFT JOIN MPA M on M.MPA_ID = F.MPA_ID " +
            "GROUP BY F.FILM_ID, F.NAME " +
            "ORDER BY COUNT(L.USER_ID) DESC " +
            "LIMIT ?";

        for (Film film : jdbcTemplate.query(sqlQuery, Mapper::mapRowToFilm, count)) {
            Set<Genre> genreSet = genreStorage.getGenres(film.getId());
            film.setGenres(genreSet);
            films.add(film);
        }
        return films;
    }
}