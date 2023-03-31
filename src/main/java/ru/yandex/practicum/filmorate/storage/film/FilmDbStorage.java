package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.utils.ClassMapper;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final Validator validator = new Validator();
    private final GenreStorage genreStorage;

    @Override
    public Film addFilm(Film film) {
        if (validator.isFilmInvalid(film)) {
            throw new FilmNotValidException(film.getId());
        }
        if (film.getGenres() != null && film.getId() != null) {
            genreStorage.setGenres(film.getId(), film.getGenres());
        }
        String sql = "INSERT INTO FILMS(name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        if (film.getGenres() != null && film.getId() != null) {
            genreStorage.setGenres(film.getId(), film.getGenres());
        }
        log.info("Film {} has been added to list", film.getId());
        return setGenres(film);
    }

    @Override
    public Film editFilm(Film film) {
        if (validator.isFilmInvalid(film)) {
            throw new FilmNotValidException(film.getId());
        }
        if (film.getGenres() != null && film.getId() != null) {
            genreStorage.setGenres(film.getId(), film.getGenres());
        }
        String sql = "UPDATE FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? " +
                "WHERE FILM_ID = ?";
        int filmStatus = jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
        if (filmStatus == 0) {
            throw new FilmNotExistException(film.getId());
        }
        log.info("Film {} has been edited", film.getId());
        return setGenres(film);
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT F.*, M.MPA_NAME FROM FILMS AS F " +
                "LEFT JOIN LIKES L on F.FILM_ID = L.FILM_ID " +
                "LEFT JOIN MPA M on M.MPA_ID = F.MPA_ID";
        List<Film> films = jdbcTemplate.query(sql, ClassMapper::rowToFilm);
        for (Film film : films) {
            setGenres(film);
        }
        log.info("All films have been uploaded");
        return films;
    }

    @Override
    public Film findFilm(Long id) {
        String sql = "SELECT F.*, M.MPA_NAME FROM FILMS AS F " +
                "LEFT JOIN LIKES L on F.FILM_ID = L.FILM_ID " +
                "LEFT JOIN MPA M on M.MPA_ID = F.MPA_ID " +
                "WHERE F.FILM_ID = ?;";
        try {
            return setGenres(Objects.requireNonNull(jdbcTemplate.queryForObject(sql, ClassMapper::rowToFilm, id)));
        } catch (Exception e) {
            log.error("Film {} was not found", id);
            throw new FilmNotExistException(id);
        }
    }

    private Film setGenres(Film film) {
        Set<Genre> genreSet = genreStorage.getGenres(film.getId());
        film.setGenres(genreSet);
        return film;
    }
}
