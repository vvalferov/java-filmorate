package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    @Override
    public Boolean addFriend(Long id, Long friendId) {
        checkUserExistence(id);
        checkUserExistence(friendId);

        friendsStorage.addFriend(id, friendId);
        return true;
    }

    @Override
    public Boolean removeFriend(Long id, Long friendId) {
        checkUserExistence(id);
        checkUserExistence(friendId);

        friendsStorage.removeFriend(id, friendId);
        return true;
    }

    @Override
    public List<User> getAllFriends(Long id) {
        return friendsStorage.findFriend(id);
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        checkUserExistence(id);
        checkUserExistence(otherId);

        return friendsStorage.findMutual(id, otherId);
    }

    private void checkUserExistence(Long userId) {
        if (userStorage.findUser(userId) == null) {
            throw new UserNotExistException(userId);
        }
    }
}
