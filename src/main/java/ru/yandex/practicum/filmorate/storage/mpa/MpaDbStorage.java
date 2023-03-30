package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.utils.Mapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Mpa get(Long id) {
        String sqlQuery = "select * from MPA where MPA_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, Mapper::mapRowToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException(id);
        }
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "select * from MPA";

        return jdbcTemplate.query(sqlQuery, Mapper::mapRowToMpa);
    }
}
