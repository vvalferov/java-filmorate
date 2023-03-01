package ru.yandex.practicum.filmorate.exceptions;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("There is no such user. Check your input");
    }
}
