package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.NewUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

@Service
public interface UserService {

    UserDto create(NewUserDto userDto);

    UserDto update(long id, UpdateUserDto updateUserDto);

    UserDto getById(long id);

    void deleteUser(long id);
}
