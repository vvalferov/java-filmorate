package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Boolean addFriend(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        return true;
    }

    @Override
    public Boolean removeFriend(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        return true;
    }

    @Override
    public List<User> getAllFriends(Long id) {
        return userStorage.getUser(id).getFriends();
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        List<User> commonFriends = new LinkedList<>();
        User user = userStorage.getUser(id);
        User other = userStorage.getUser(otherId);
        if (user.getFriends().isEmpty() || other.getFriends().isEmpty())
            return new LinkedList<>();
        for (User friend : user.getFriends()) {
            if (other.getFriends().contains(friend))
                commonFriends.add(friend);
        }
        return commonFriends;
    }
}
