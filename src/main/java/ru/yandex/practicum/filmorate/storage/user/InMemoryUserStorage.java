package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final List<User> allUsers = new ArrayList<>();
    private long currentId = 1;

    @Override
    public User addUser(User user) {
        if (isUserValid(user) && !allUsers.contains(user)) {
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
        if (!isUserValid(user))
            throw new UserNotValidException(user.getId());
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

    private boolean isUserValid(User user) {
        if (user.getLogin().contains(" ") || user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Fields of user {} seem to be invalid", user.getLogin());
            return false;
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("User's name was changed to {}", user.getName());
        }
        return true;
    }

    private long getCurrentId() {
        return currentId++;
    }
}
