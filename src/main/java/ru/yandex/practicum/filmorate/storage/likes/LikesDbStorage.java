package ru.yandex.practicum.filmorate.storage.likes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.utils.ClassMapper;

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
    public List<Long> getLike(Long filmId) {
        log.info("Entered GET likes");
        String sql = "SELECT USER_ID FROM LIKES WHERE FILM_ID = ?";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> resultSet.getLong("user_id"), filmId);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        log.info("Entered POST like");
        String sql = "INSERT INTO LIKES(film_id, user_id) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, filmId, userId);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sql = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        List<Film> films = new LinkedList<>();
        String sql = "SELECT F.*, M.MPA_NAME FROM FILMS AS F " +
                "LEFT JOIN LIKES L ON F.FILM_ID = L.FILM_ID " +
                "LEFT JOIN MPA M ON M.MPA_ID = F.MPA_ID " +
                "GROUP BY F.FILM_ID, F.NAME " +
                "ORDER BY COUNT(L.USER_ID) DESC " +
                "LIMIT ?";

        for (Film film : jdbcTemplate.query(sql, ClassMapper::rowToFilm, count)) {
            Set<Genre> genreSet = genreStorage.getGenres(film.getId());
            film.setGenres(genreSet);
            films.add(film);
        }
        return films;
    }
}