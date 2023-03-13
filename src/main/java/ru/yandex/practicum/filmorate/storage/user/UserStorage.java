package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public interface UserStorage {
    User addUser(User user);

    User editUser(User user);

    User findUser(Long id);

    List<User> getAll();
}
