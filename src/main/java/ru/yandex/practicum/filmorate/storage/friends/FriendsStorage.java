package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {
    List<User> getFriend(Long id);

    void addFriend(Long id, Long friendId);

    List<User> getMutual(Long id, Long friendId);

    void removeFriend(Long id, Long friendId);
}
