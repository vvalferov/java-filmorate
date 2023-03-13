package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
public interface FilmStorage {
    Film addFilm(Film film);

    Film editFilm(Film film);

    Film findFilm(Long id);

    List<Film> getAll();
}
