package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {
    List<User> get(Long id);
    void add(Long id, Long friendId);
    List<User> getMutual(Long id, Long friendId);
    void remove(Long id, Long friendId);
}
