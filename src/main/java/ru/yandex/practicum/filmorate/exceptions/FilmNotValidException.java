package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotValidException extends RuntimeException {
    public FilmNotValidException() {
        super("This film is invalid. Check your input");
    }
}
