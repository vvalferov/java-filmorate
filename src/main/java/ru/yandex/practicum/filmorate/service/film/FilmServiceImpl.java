package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    public final FilmStorage filmStorage;

    @Override
    public Boolean addLike(Long id, Long userId) {
        Film film = filmStorage.findFilm(id);
        film.getLikes().add(userId);
        return true;
    }

    @Override
    public Boolean removeLike(Long id, Long userId) {
        Film film = filmStorage.findFilm(id);
        if (film.getLikes().contains(userId))
            film.getLikes().remove(userId);
        else
            throw new UserNotExistException(userId);
        return true;
    }

    @Override
    public Collection<Film> getMostPopularFilms(Integer count) {
        List<Film> films = new LinkedList<>(filmStorage.getAll());
        Comparator<Film> comparator = Comparator.comparingInt(Film::getLikesCount).reversed();
        films.sort(comparator);
        if (count > films.size())
            count = films.size();
        return films.subList(0, count);
    }
}
