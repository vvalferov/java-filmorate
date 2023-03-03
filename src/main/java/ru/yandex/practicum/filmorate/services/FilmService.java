package ru.yandex.practicum.filmorate.services;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
public interface FilmService {
    Film addFilm(Film film);

    Film editFilm(Film film);

    List<Film> getAll();
}
