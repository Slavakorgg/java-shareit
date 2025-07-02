package ru.practicum.shareit.practicum.shareit.user;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.practicum.shareit.user.dto.NewUserDto;
import ru.practicum.shareit.practicum.shareit.user.dto.UpdateUserDto;


public class UserClient extends BaseClient {

    public UserClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> create(NewUserDto userDto) {
        return post("", userDto);
    }

    public ResponseEntity<Object> getById(Long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> update(Long userId, UpdateUserDto userDto) {
        return patch("/" + userId, userId, userDto);
    }

    public ResponseEntity<Object> deleteUser(Long userId) {
        return delete("/" + userId, userId);
    }
}
