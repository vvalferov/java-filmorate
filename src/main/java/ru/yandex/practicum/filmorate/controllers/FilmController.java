package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotValidException;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmServiceImpl;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmServiceImpl filmService;

    @GetMapping("/{id}")
    public ResponseEntity<Film> findUser(@PathVariable Long id) {
        log.info("Entered GET film");
        return ResponseEntity.ok().body(filmService.filmStorage.findFilm(id));
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        log.info("Entered POST film");
        return ResponseEntity.ok().body(filmService.filmStorage.addFilm(film));
    }

    @PutMapping
    public ResponseEntity<Film> editFilm(@Valid @RequestBody Film film) {
        log.info("Entered PUT film");
        return ResponseEntity.ok().body(filmService.filmStorage.editFilm(film));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Boolean> addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Entered PUT like film");
        return ResponseEntity.ok().body(filmService.addLike(id, userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Boolean> removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Entered DELETE like film");
        return ResponseEntity.ok().body(filmService.removeLike(id, userId));
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Entered GET film");
        return ResponseEntity.ok().body(filmService.getMostPopularFilms(count));
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        log.info("Entered GET film");
        return ResponseEntity.ok().body(filmService.filmStorage.getAll());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> handleNotExistingFilm(final FilmNotExistException e) {
        return Map.of("error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Map<String, String> handleNotValidFilm(final FilmNotValidException e) {
        return Map.of("error", e.getMessage());
    }
}
