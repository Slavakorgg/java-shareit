package ru.practicum.shareit.user.dto;


import lombok.*;


@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
