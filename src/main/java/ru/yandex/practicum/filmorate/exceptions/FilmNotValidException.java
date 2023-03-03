package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotValidException extends RuntimeException {
    public FilmNotValidException(long id) {
        super("This film " + id + " is invalid. Check your input");
    }
}
