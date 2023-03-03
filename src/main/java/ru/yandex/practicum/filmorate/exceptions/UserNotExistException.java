package ru.yandex.practicum.filmorate.exceptions;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(long id) {
        super("There is no " + id + " user. Check your input");
    }
}
