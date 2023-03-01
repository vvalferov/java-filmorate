package ru.yandex.practicum.filmorate.exceptions;

public class UserNotValidException extends RuntimeException {
    public UserNotValidException() {
        super("This user is invalid. Check your input");
    }
}
