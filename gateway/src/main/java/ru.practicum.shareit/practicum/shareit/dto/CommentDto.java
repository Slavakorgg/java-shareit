package ru.practicum.shareit.practicum.shareit.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.practicum.shareit.item.Item;

import java.time.Instant;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private Item item;
    private String authorName;
    private Instant created;
}
