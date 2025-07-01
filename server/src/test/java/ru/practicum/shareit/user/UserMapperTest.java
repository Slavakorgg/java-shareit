package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.NewUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    @Test
    void userMapToDtoTest() {
        User user = new User();
        user.setId(1L);
        user.setName("Tom");
        user.setEmail("tom@mail.ru");
        UserMapper mapper = new UserMapper();
        assertEquals(mapper.mapToUserDto(user).getId(), 1);
        assertEquals(mapper.mapToUserDto(user).getName(), "Tom");
        assertEquals(mapper.mapToUserDto(user).getEmail(), "tom@mail.ru");
    }

    @Test
    void newUserMapTest() {
        NewUserDto user = new NewUserDto("Tom", "tom@mail.ru");
        UserMapper mapper = new UserMapper();
        assertEquals(mapper.mapToNewUser(user).getName(), "Tom");
        assertEquals(mapper.mapToNewUser(user).getEmail(), "tom@mail.ru");
    }

    @Test
    void updateUserMapTest() {
        UpdateUserDto updateUserDto = new UpdateUserDto("Tom", "tom@mail.ru");
        User user = new User();
        user.setId(1L);
        user.setName("Sam");
        user.setEmail("sam@mail.ru");
        UserMapper mapper = new UserMapper();
        assertEquals(mapper.mapToUserUpdate(user, updateUserDto).getName(), "Tom");
        assertEquals(mapper.mapToUserUpdate(user, updateUserDto).getEmail(), "tom@mail.ru");

    }

    @Test
    void userMapTest() {
        UserDto user = new UserDto(1L, "Tom", "tom@mail.ru");
        UserMapper mapper = new UserMapper();
        assertEquals(mapper.mapToUser(user).getName(), "Tom");
        assertEquals(mapper.mapToUser(user).getEmail(), "tom@mail.ru");
    }

}

