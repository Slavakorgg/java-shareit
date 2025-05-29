package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("==> Creating user: {}", userDto);
        UserDto user = userService.create(userDto);
        log.info("<== Creating user: {}", user);
        return user;
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable("userId") Long userId, @Validated @RequestBody(required = false) UserDto userDto) {
        log.info("==> Updating user: {}", userDto);
        UserDto user = userService.update(userId, userDto);
        log.info("<== Updating user: {}", user);
        return user;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        log.info("==> get User by id: {}", userId);
        UserDto user = userService.getUserById(userId);
        log.info("<== get User by id: {}", user);
        return user;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("userId") Long userId) {
        log.info("==> delete User by id: {}", userId);
        userService.deleteUser(userId);
        log.info("<== delete User by id: {}", userId);
    }
}
