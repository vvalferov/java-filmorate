package ru.yandex.practicum.filmorate.exceptions;

public class UserNotValidException extends RuntimeException {
    public UserNotValidException(Long id) {
        super("This user " + id + " is invalid. Check your input");
    }
}
