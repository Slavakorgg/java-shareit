package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private Long id;
    private String name;
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;
}
