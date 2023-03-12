package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public interface UserService {
    Boolean addFriend(Long id, Long friendId);

    Boolean removeFriend(Long id, Long friendId);

    List<User> getAllFriends(Long id);

    List<User> getCommonFriends(Long id, Long otherId);
}
