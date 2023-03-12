package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FilmNotExistException extends RuntimeException {
    public FilmNotExistException(long id) {
        super("There is no " + id + " film. Check your input");
    }
}
