package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto create(NewUserDto userDto) {
        log.debug("==> Creating user: {}", userDto);
        User user = UserMapper.mapToNewUser(userDto);
        userRepository.save(user);
        log.debug("<== Creating user: {}", user);
        return UserMapper.mapToUserDto(user);

    }

    @Override
    public UserDto update(long id, UpdateUserDto updateUserDto) {
        log.debug("==> Updating user: {}", id);
        User oldUser = UserMapper.mapToUser(getById(id));
        User updateUser = UserMapper.mapToUserUpdate(oldUser, updateUserDto);
        updateUser = userRepository.save(updateUser);
        log.debug("<== Updating user: {}", id);
        return UserMapper.mapToUserDto(updateUser);
    }

    @Override
    public UserDto getById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public void deleteUser(long id) {
        log.debug("==> Deleting user: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));
        if (user != null) {
            userRepository.delete(user);
            log.debug("<== Deleting user: {}", id);
        }
    }


}
