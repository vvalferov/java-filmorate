package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Mapper;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final Validator validator = new Validator();

    @Override
    public User addUser(User user) {
        if (validator.isUserInvalid(user))
            throw new UserNotValidException(user.getId());
        String sqlQuery = "insert into USERS(email, login, birthday, name) " +
                "values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[] {"user_id"});
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getLogin());
                stmt.setDate(3, Date.valueOf(user.getBirthday()));
                stmt.setString(4, user.getName());
                return stmt;
            }, keyHolder);

            if (Objects.isNull(keyHolder.getKey())) {
                throw new UserNotExistException(user.getId());
            }

            user.setId(keyHolder.getKey().longValue());

            return user;
        } catch (DuplicateKeyException e) {
            throw new UserNotValidException(user.getId());
        }
    }

    @Override
    public User editUser(User user) {
        if (validator.isUserInvalid(user))
            throw new UserNotValidException(user.getId());
        String sqlQuery = "update USERS set " +
                "EMAIL = ?, LOGIN = ?, BIRTHDAY = ?, NAME = ? " +
                "where USER_ID = ?";

        int status = jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getName(),
                user.getId()
        );

        if (status == 0) {
            throw new UserNotExistException(user.getId());
        }

        return user;
    }

    @Override
    public User findUser(Long id) {
        String sqlQuery = "select * " +
                "from USERS " +
                "where USER_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, Mapper::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotExistException(id);
        }
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "select * from USERS";

        return jdbcTemplate.query(sqlQuery, Mapper::mapRowToUser);
    }
}
