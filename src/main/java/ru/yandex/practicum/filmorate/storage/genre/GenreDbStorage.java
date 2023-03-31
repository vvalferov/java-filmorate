package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.utils.ClassMapper;

import java.sql.PreparedStatement;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre getGenre(Long id) {
        String sql = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ClassMapper::rowToGenre, id);
        } catch (Exception e) {
            throw new GenreNotFoundException(id);
        }
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sql, ClassMapper::rowToGenre);
    }

    @Override
    public Set<Genre> getGenres(Long filmId) {
        String sql = "SELECT G.* FROM ALL_GENRES AG " +
                "JOIN GENRE G on G.GENRE_ID = AG.GENRE_ID " +
                "WHERE FILM_ID = ? " +
                "ORDER BY GENRE_ID";
        return new LinkedHashSet<>(jdbcTemplate.query(sql, ClassMapper::rowToGenre, filmId));
    }

    @Override
    public Map<Long, Set<Genre>> getGenresAsMap(List<Film> films) {
        Map<Long, Set<Genre>> allGenres = new HashMap<>();

        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        String sql = "SELECT AG.*, G.GENRE_NAME FROM ALL_GENRES AG " +
                "JOIN GENRE G on G.GENRE_ID = AG.GENRE_ID " +
                "WHERE film_id IN (%s)";
        jdbcTemplate.query(
                String.format(sql, inSql),
                (resultSet, rowNum) -> {
                    Long filmId = resultSet.getLong("film_id");
                    Genre genre = ClassMapper.rowToGenre(resultSet, rowNum);
                    Set<Genre> genres = allGenres.getOrDefault(filmId, new LinkedHashSet<>());
                    genres.add(genre);
                    return allGenres.put(filmId, genres);
                },
                films.stream().map(film -> film.getId().toString()).toArray()
        );
        return allGenres;
    }

    @Override
    public void setGenres(Long filmId, Set<Genre> genres) {
        removeGenres(filmId);
        String sql = "INSERT INTO ALL_GENRES(FILM_ID, GENRE_ID) VALUES (?, ?)";
        try {
            jdbcTemplate.batchUpdate(sql, genres, genres.size(), (PreparedStatement statement, Genre genre) -> {
                statement.setLong(1, filmId);
                statement.setLong(2, genre.getId());
            });
        } catch (Exception ignored) {
        }
    }

    @Override
    public void removeGenres(Long filmId) {
        String sql = "DELETE FROM ALL_GENRES WHERE ALL_GENRES.FILM_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }
}
