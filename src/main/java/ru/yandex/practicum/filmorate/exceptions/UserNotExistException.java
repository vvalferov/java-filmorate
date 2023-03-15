package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotExistException extends RuntimeException {
    public UserNotExistException(long id) {
        super("There is no " + id + " user. Check your input");
    }
}
