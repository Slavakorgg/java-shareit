package ru.practicum.shareit.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.practicum.shareit.dto.CommentDto;
import ru.practicum.shareit.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Data
@Builder
public class ItemWithCommentsDto {
    private long id;

    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;
    private Long requestId;
}
