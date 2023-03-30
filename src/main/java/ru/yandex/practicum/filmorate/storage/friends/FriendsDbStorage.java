package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Mapper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> get(Long id) {
        String sqlQuery = "select U.* " +
            "from FRIEND_STATUS as FS " +
            "join USERS as U on FS.FRIEND_ID = U.USER_ID " +
            "where FS.USER_ID = ?";
        return jdbcTemplate.query(sqlQuery, Mapper::mapRowToUser, id);
    }

    @Override
    public void add(Long id, Long friendId) {
        String sqlQuery = "insert into FRIEND_STATUS(user_id, friend_id) " +
            "values ( ?, ? )";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public List<User> getMutual(Long id, Long friendId) {
        String sqlQuery = "SELECT u.* " +
            "FROM USERS u, FRIEND_STATUS f, FRIEND_STATUS o " +
            "WHERE u.USER_ID = f.FRIEND_ID " +
            "  AND u.USER_ID = o.FRIEND_ID " +
            "  AND f.USER_ID = ? " +
            "  AND o.USER_ID = ?";
        return jdbcTemplate.query(sqlQuery, Mapper::mapRowToUser, id, friendId);
    }

    @Override
    public void remove(Long id, Long friendId) {
        String sqlQuery = "delete from FRIEND_STATUS " +
            "where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }
}
