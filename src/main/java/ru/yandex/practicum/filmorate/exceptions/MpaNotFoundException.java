package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MpaNotFoundException extends RuntimeException {
    public MpaNotFoundException(long id) {
        super("There is no " + id + " mpa. Check your input");
    }
}
