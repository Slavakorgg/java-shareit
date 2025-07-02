package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;


import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImplTest {
    private final UserServiceImpl userService;

    private NewUserDto newUserDto;
    private NewUserDto newUserDto2;
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
    void addUserWithoutEmailTest() {
        newUserDto = new NewUserDto("Don", "don@example.com");
        newUserDto.setEmail(null);

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class,
                () -> userService.create(newUserDto)
        );

    }

    @Test
    void addUserWithSameEmailTest() {
        newUserDto = new NewUserDto("Don", "don@example.com");
        newUserDto2 = new NewUserDto("Don2", "don@example.com");
        userService.create(newUserDto);


        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class,
                () -> userService.create(newUserDto2)
        );

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
    void deleteTest() {
        userService.deleteUser(1);

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> userService.getById(1)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь с id = 1 не найден");

    }

    @Test
    void deleteWrongIdTest() {

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> userService.deleteUser(1000)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь с id = 1000 не найден");

    }
}