package ru.yandex.practicum.filmorate.services;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public interface UserService {
    User addUser(User user);

    User editUser(User user);

    List<User> getAll();
}
