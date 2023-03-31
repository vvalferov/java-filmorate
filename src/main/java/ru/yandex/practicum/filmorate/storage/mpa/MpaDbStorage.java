package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.utils.ClassMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getMpa(Long id) {
        String sql = "SELECT * FROM MPA WHERE MPA_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ClassMapper::rowToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            log.info("MPA {} was not found", id);
            throw new MpaNotFoundException(id);
        }
    }

    @Override
    public List<Mpa> getAll() {
        String sql = "SELECT * FROM MPA";
        return jdbcTemplate.query(sql, ClassMapper::rowToMpa);
    }
}
