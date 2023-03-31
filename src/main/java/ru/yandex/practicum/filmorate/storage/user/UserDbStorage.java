package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ClassMapper;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final Validator validator = new Validator();

    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (validator.isUserInvalid(user))
            throw new UserNotValidException(user.getId());
        String sql = "INSERT INTO USERS(email, login, name, birthday) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(sql, new String[]{"user_id"});
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getName());
                statement.setDate(4, Date.valueOf(user.getBirthday()));
                return statement;
            }, keyHolder);
            if (keyHolder.getKey() == null) {
                throw new UserNotExistException(user.getId());
            }
            user.setId(keyHolder.getKey().longValue());
            return user;
        } catch (Exception e) {
            throw new UserNotValidException(user.getId());
        }
    }

    @Override
    public User editUser(User user) {
        if (validator.isUserInvalid(user))
            throw new UserNotValidException(user.getId());
        String sql = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        if (jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId()) < 1) {
            throw new UserNotExistException(user.getId());
        }
        return user;
    }

    @Override
    public User findUser(Long id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ClassMapper::rowToUser, id);
        } catch (Exception e) {
            throw new UserNotExistException(id);
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, ClassMapper::rowToUser);
    }
}
