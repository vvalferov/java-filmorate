package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.exceptions.FilmNotValidException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    public boolean isFilmInvalid(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmNotValidException(film.getId());
        }
        return false;
    }

    public boolean isUserInvalid(User user) {
        if (user.getLogin().contains(" ") || user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserNotValidException(user.getId());
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return false;
    }
}
