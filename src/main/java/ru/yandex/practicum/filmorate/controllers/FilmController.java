package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmServiceImpl;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    final FilmServiceImpl filmService;

    @Autowired
    public FilmController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getUser(@PathVariable Long id) {
        log.info("Entered GET film");
        return ResponseEntity.ok().body(filmService.filmStorage.getFilm(id));
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
}
