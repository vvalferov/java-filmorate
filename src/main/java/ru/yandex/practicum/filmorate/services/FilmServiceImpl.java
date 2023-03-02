package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotValidException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final List<Film> allFilms = new ArrayList<>();

    @Override
    public Film addFilm(Film film) {
        if (isFilmValid(film) && !allFilms.contains(film)) {
            long id = film.getId();
            allFilms.add(film);
            log.info("Film {} has been added to list", id);
            return getFilmById(id);
        } else
            throw new FilmNotValidException(film.getId());
    }

    @Override
    public Film editFilm(Film film) {
        if (!isFilmValid(film))
            throw new FilmNotValidException(film.getId());
        long id = film.getId();
        Film oldFilm = getFilmById(id);
        //Film oldFilm = allFilms.get(0);
        if (oldFilm != null) {
            allFilms.remove(oldFilm);
            allFilms.add(film);
            log.info("Film {} has been edited", id);
            return getFilmById(id);
        } else {
            throw new FilmNotExistException(id);
        }
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
        return null;
    }

    private boolean isFilmValid(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Fields of film {} seem to be invalid", film.getId());
            return false;
        }
        return true;
    }
}
