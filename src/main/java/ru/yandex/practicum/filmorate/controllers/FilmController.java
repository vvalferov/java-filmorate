package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;
import ru.yandex.practicum.filmorate.services.FilmServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/films/")
public class FilmController {
    //ругался на Autowired, сказал, что field injection is not recommended
    private final FilmService filmService = new FilmServiceImpl();

    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Film> addFilm(@RequestBody Film film) {
        return ResponseEntity.ok().body(filmService.addFilm(film));
    }

    @PutMapping(value = "/edit", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Film> editFilm(@RequestBody Film film) {
        return ResponseEntity.ok().body(filmService.editFilm(film));
    }

    @GetMapping(value = "/get_all", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok().body(filmService.getAll());
    }
}
