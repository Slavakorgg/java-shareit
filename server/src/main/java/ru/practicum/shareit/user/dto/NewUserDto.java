package ru.practicum.shareit.user.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserDto {

    private String name;

    private String email;
}
