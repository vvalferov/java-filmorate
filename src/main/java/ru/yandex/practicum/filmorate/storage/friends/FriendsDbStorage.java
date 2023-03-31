package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ClassMapper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getFriend(Long id) {
        String sql = "SELECT U.* " +
                "FROM FRIEND_STATUS AS F " +
                "JOIN USERS AS U ON F.FRIEND_ID = U.USER_ID " +
                "WHERE F.USER_ID = ?";
        log.info("Entered GET friend");
        return jdbcTemplate.query(sql, ClassMapper::rowToUser, id);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        String sql = "INSERT INTO FRIEND_STATUS(user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<User> getMutual(Long id, Long friendId) {
        String sql = "SELECT u.* FROM USERS u, FRIEND_STATUS f, FRIEND_STATUS s " +
                "WHERE u.USER_ID = f.FRIEND_ID " +
                "  AND u.USER_ID = s.FRIEND_ID " +
                "  AND f.USER_ID = ? " +
                "  AND s.USER_ID = ?";
        return jdbcTemplate.query(sql, ClassMapper::rowToUser, id, friendId);
    }

    @Override
    public void removeFriend(Long id, Long friendId) {
        String sql = "DELETE FROM FRIEND_STATUS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, id, friendId);
    }
}
