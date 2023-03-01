package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotExistException extends RuntimeException {
    public FilmNotExistException() {
        super("There is no such film. Check your input");
    }
}
