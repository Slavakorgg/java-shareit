package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userRepository.save(user);

        return userMapper.toUserDto(user);

    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        User updateUser = userMapper.toUser(userDto);

        if (updateUser.getName() != null && !Objects.equals(user.getName(), updateUser.getName())) {
            user.setName(updateUser.getName());
        }
        if (updateUser.getEmail() != null && !Objects.equals(user.getEmail(), updateUser.getEmail())) {
            user.setEmail(updateUser.getEmail());
        }
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
