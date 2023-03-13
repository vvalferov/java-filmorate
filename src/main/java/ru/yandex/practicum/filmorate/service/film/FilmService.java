package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Boolean addLike(Long id, Long userId);

    Boolean removeLike(Long id, Long userId);

    Collection<Film> getMostPopularFilms(Integer count);
}
