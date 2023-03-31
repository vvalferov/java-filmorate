package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.likes.LikesStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    public final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;

    @Override
    public Boolean addLike(Long id, Long userId) {
        checkExistence(id, userId);
        likesStorage.addLike(id, userId);
        return true;
    }

    @Override
    public Boolean removeLike(Long id, Long userId) {
        checkExistence(id, userId);
        likesStorage.removeLike(id, userId);
        return true;
    }

    @Override
    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> films = likesStorage.getMostPopularFilms(count);
        Map<Long, Set<Genre>> genres = genreStorage.getGenresAsMap(films);
        for (Film film : films) {
            film.setGenres(genres.getOrDefault(film.getId(), new LinkedHashSet<>()));
        }
        return films;
    }

    private void checkExistence(Long filmId, Long userId) {
        if (filmStorage.findFilm(filmId) == null) {
            throw new FilmNotExistException(filmId);
        }
        if (userStorage.findUser(userId) == null) {
            throw new UserNotValidException(userId);
        }
    }
}
