package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        log.info("Entered POST film");
        return ResponseEntity.ok().body(filmService.addFilm(film));
    }

    @PutMapping
    public ResponseEntity<Film> editFilm(@Valid @RequestBody Film film) {
        log.info("Entered PUT film");
        return ResponseEntity.ok().body(filmService.editFilm(film));
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        log.info("Entered GET film");
        return ResponseEntity.ok().body(filmService.getAll());
    }
}
