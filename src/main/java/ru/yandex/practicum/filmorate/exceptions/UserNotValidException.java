package ru.yandex.practicum.filmorate.exceptions;

public class UserNotValidException extends RuntimeException {
    public UserNotValidException(long id) {
        super("This user " + id + " is invalid. Check your input");
    }
}
