package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserStorage userStorage;

    @Override
    public Boolean addFriend(Long id, Long friendId) {
        User user = userStorage.findUser(id);
        User friend = userStorage.findUser(friendId);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        return true;
    }

    @Override
    public Boolean removeFriend(Long id, Long friendId) {
        User user = userStorage.findUser(id);
        User friend = userStorage.findUser(friendId);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        return true;
    }

    @Override
    public List<User> getAllFriends(Long id) {
        return userStorage.findUser(id).getFriends();
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        List<User> commonFriends = new LinkedList<>();
        User user = userStorage.findUser(id);
        User other = userStorage.findUser(otherId);
        if (user.getFriends().isEmpty() || other.getFriends().isEmpty())
            return new LinkedList<>();
        for (User friend : user.getFriends()) {
            if (other.getFriends().contains(friend))
                commonFriends.add(friend);
        }
        return commonFriends;
    }
}
