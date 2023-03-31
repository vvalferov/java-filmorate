package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    Boolean addLike(Long id, Long userId);

    Boolean removeLike(Long id, Long userId);

    List<Film> getMostPopularFilms(Integer count);
}
