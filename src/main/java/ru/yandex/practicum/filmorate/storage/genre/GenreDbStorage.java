package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.utils.Mapper;

import java.sql.PreparedStatement;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre get(Long id) {
        String sqlQuery = "select * from GENRE WHERE GENRE_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Mapper::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException(id);
        }
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "select * from GENRE";
        return jdbcTemplate.query(sqlQuery, Mapper::mapRowToGenre);
    }

    @Override
    public Set<Genre> getGenres(Long filmId) {
        String sqlQuery = "SELECT G.* " +
            "FROM ALL_GENRES AG " +
            "JOIN GENRE G on G.GENRE_ID = AG.GENRE_ID " +
            "WHERE FILM_ID = ? " +
            "ORDER BY GENRE_ID";

        return new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, Mapper::mapRowToGenre, filmId));
    }

    @Override
    public Map<Long, Set<Genre>> getGenres(List<Film> films) {
        Map<Long, Set<Genre>> genresById = new HashMap<>();

        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        String sqlQuery = "SELECT AG.*, G.GENRE_NAME " +
            "FROM ALL_GENRES AG " +
            "JOIN GENRE G on G.GENRE_ID = AG.GENRE_ID " +
            "WHERE film_id IN (%s)";

        jdbcTemplate.query(
            String.format(sqlQuery, inSql),
            (rs, rowNum) -> {
                Long filmId = rs.getLong("film_id");
                Genre filmGenre = Mapper.mapRowToGenre(rs, rowNum);
                Set<Genre> genres = genresById.getOrDefault(filmId, new LinkedHashSet<>());
                genres.add(filmGenre);

                return genresById.put(
                    filmId,
                    genres
                );
            },
            films.stream().map(film -> film.getId().toString()).toArray()
        );

        return genresById;
    }

    @Override
    public void setGenres(Long filmId, Set<Genre> genres) {
        if (Objects.isNull(genres)) {
            return;
        }

        removeGenres(filmId);

        if (genres.isEmpty()) {
            return;
        }

        String sqlQuery = "insert into ALL_GENRES(FILM_ID, GENRE_ID) " +
            "values ( ?, ? )";

        try {
            jdbcTemplate.batchUpdate(sqlQuery, genres, genres.size(), (PreparedStatement ps, Genre genre) -> {
                ps.setLong(1, filmId);
                ps.setLong(2, genre.getId());
            });
        } catch (DuplicateKeyException ignored) {
        }
    }

    @Override
    public void removeGenres(Long filmId) {
        String sqlQuery = "delete from ALL_GENRES " +
            "where ALL_GENRES.FILM_ID = ?";

        jdbcTemplate.update(sqlQuery, filmId);
    }
}
