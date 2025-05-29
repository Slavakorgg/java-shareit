package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    private final AtomicLong idCounter = new AtomicLong();

    public User save(User user) {
        validateUser(user);
        validateEmail(user);
        if (user.getId() == null) {
            user.setId(idCounter.incrementAndGet());
            users.put(user.getId(), user);

        } else {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);

            } else {
                throw new NotFoundException("Пользователь не найден");
            }
        }
        return user;
    }

    public User findById(Long id) {
        return users.get(id);
    }

    public void deleteById(Long id) {
        users.remove(id);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || !(user.getEmail().contains("@"))) {
            throw new ValidationException("Некорректный email");
        }
        if (user.getName() == null || user.getName().equals("")) {
            throw new NotFoundException("Имя не может быть пустым");
        }

    }

    private void validateEmail(User user) {
        for (User u : users.values()) {
            if (u.getId() == user.getId()) {
                continue;
            }
            if (u.getEmail().equals(user.getEmail())) {
                throw new ConflictException("Этот email уже используется");
            }
        }
    }

}
