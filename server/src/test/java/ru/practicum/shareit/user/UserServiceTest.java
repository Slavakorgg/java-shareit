package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;


import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    private final UserService userService;

    private NewUserDto newUserDto;
    private UpdateUserDto updateUserDto;

    @Test
    void addUserTest() {
        newUserDto = new NewUserDto("Don", "don@example.com");
        UserDto userDto = userService.create(newUserDto);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(4, userDto.getId());
        Assertions.assertEquals("Don", userDto.getName());
        Assertions.assertEquals("don@example.com", userDto.getEmail());
    }


    @Test
    void updateUserTest() {
        updateUserDto = new UpdateUserDto("Tom", "tom@example.com");
        UserDto userDto = userService.update(3L, updateUserDto);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(3, userDto.getId());
        Assertions.assertEquals("Tom", userDto.getName());
        Assertions.assertEquals("tom@example.com", userDto.getEmail());
    }

    @Test
    void updateUserNameTest() {
        UserDto userDto = userService.update(1L, UpdateUserDto.builder().name("new_user_name").build());

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(1, userDto.getId());
        Assertions.assertEquals("new_user_name", userDto.getName());
        Assertions.assertEquals("new_user@example.com", userDto.getEmail());
    }

    @Test
    void updateUserEmailTest() {
        UserDto userDto = userService.update(1L, UpdateUserDto.builder().email("new_user_email@example.com").build());

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(1, userDto.getId());
        Assertions.assertEquals("new_user", userDto.getName());
        Assertions.assertEquals("new_user_email@example.com", userDto.getEmail());
    }

    @Test
    void updateUserWithWrongUserIdTest() {
        updateUserDto = new UpdateUserDto("Tom", "tom@example.com");
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> userService.update(1000L, updateUserDto)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь с id = 1000 не найден");

    }


    @Test
    void getUserByIdTest() {
        UserDto userDto = userService.getById(1);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("new_user", userDto.getName());
        Assertions.assertEquals("new_user@example.com", userDto.getEmail());

    }

    @Test
    void getUserByWrongId() {
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> userService.getById(1000L)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь с id = 1000 не найден");
    }


    @Test
    void deleteUserById() {
        userService.deleteUser(1);

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> userService.getById(1)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь с id = 1 не найден");

    }
}