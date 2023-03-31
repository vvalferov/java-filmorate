package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final List<User> allUsers = new ArrayList<>();
    private long currentId = 1;
    private final Validator validator = new Validator();

    @Override
    public User addUser(User user) {
        if (!validator.isUserInvalid(user) && !allUsers.contains(user)) {
            Long id = user.getId();
            if (id == null) {
                user.setId(getCurrentId());
                id = user.getId();
            }
            allUsers.add(user);
            log.info("User {} has been added to list", id);
            return getUserById(id);
        }
        throw new UserNotValidException(user.getId());
    }

    @Override
    public User editUser(User user) {
        if (validator.isUserInvalid(user)) {
            throw new UserNotValidException(user.getId());
        }
        long id = user.getId();
        User oldUser = getUserById(id);
        allUsers.remove(oldUser);
        allUsers.add(user);
        log.info("User {} has been edited", id);
        return getUserById(id);
    }

    @Override
    public User findUser(Long id) {
        return getUserById(id);
    }

    @Override
    public List<User> getAll() {
        log.info("All users have been uploaded");
        return allUsers;
    }

    private User getUserById(Long id) {
        for (User user : allUsers) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        log.error("User {} was not found", id);
        throw new UserNotExistException(id);
    }

    private long getCurrentId() {
        return currentId++;
    }
}
