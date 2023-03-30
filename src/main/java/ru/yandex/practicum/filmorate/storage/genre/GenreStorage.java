package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface GenreStorage {
    Genre get(Long id);
    List<Genre> getAll();
    Set<Genre> getGenres(Long filmId);
    Map<Long, Set<Genre>> getGenres(List<Film> films);
    void setGenres(Long filmId, Set<Genre> genres);
    void removeGenres(Long filmId);
}
