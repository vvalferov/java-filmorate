package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotExistException extends RuntimeException {
    public FilmNotExistException(long id) {
        super("There is no " + id + " film. Check your input");
    }
}
