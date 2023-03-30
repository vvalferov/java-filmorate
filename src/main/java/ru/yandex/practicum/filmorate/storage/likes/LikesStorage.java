package ru.yandex.practicum.filmorate.storage.likes;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {
    List<Long> get(Long filmId);
    void add(Long filmId, Long userId);
    void remove(Long filmId, Long userId);
    List<Film> getMostPopularFilms(int count);
}
