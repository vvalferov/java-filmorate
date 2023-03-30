package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> allFilms = new ArrayList<>();
    private long currentId = 1;
    private final Validator validator = new Validator();

    @Override
    public Film addFilm(Film film) {
        if (!validator.isFilmInvalid(film) && !allFilms.contains(film)) {
            Long id = film.getId();
            if (id == null) {
                film.setId(getCurrentId());
                id = film.getId();
            }
            allFilms.add(film);
            log.info("Film {} has been added to list", id);
            return getFilmById(id);
        } else
            throw new FilmNotValidException(film.getId());
    }

    @Override
    public Film editFilm(Film film) {
        if ((validator.isFilmInvalid(film)))
            throw new FilmNotValidException(film.getId());
        long id = film.getId();
        Film oldFilm = getFilmById(id);
        allFilms.remove(oldFilm);
        allFilms.add(film);
        log.info("Film {} has been edited", id);
        return getFilmById(id);
    }

    @Override
    public Film findFilm(Long id) {
        return getFilmById(id);
    }

    @Override
    public List<Film> getAll() {
        log.info("All films have been uploaded");
        return allFilms;
    }

    private Film getFilmById(long id) {
        for (Film film : allFilms) {
            if (film.getId() == id) {
                return film;
            }
        }
        log.error("Film {} was not found", id);
        throw new FilmNotExistException(id);
    }

    private long getCurrentId() {
        return currentId++;
    }
}
