package ru.yandex.practicum.filmorate.exceptions;

public class NotValidUserException extends RuntimeException {
    public NotValidUserException() {
        super("This user is invalid. Check your input");
    }
}
