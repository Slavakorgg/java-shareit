package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("==> Creating user: {}", userDto);
        User user = userMapper.toUser(userDto);
        userRepository.save(user);
        log.debug("<== Creating user: {}", user);
        return userMapper.toUserDto(user);

    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        log.debug("==> Updating user: {}", userDto);
        User user = userExist(id);
        User updateUser = userMapper.toUser(userDto);

        if (updateUser.getName() != null && !Objects.equals(user.getName(), updateUser.getName())) {
            user.setName(updateUser.getName());
        }
        if (updateUser.getEmail() != null && !Objects.equals(user.getEmail(), updateUser.getEmail())) {
            user.setEmail(updateUser.getEmail());
        }
        userRepository.save(user);
        log.debug("<== Updating user: {}", user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto get(Long id) {
        log.debug("==> get User by id: {}", id);
        User user = userExist(id);
        log.debug("<== get User by id: {}", user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("==> delete User by id: {}", id);
        userRepository.deleteById(id);
        log.info("<== delete User by id: {}", id);
    }

    public User userExist(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        return user;
    }


}
