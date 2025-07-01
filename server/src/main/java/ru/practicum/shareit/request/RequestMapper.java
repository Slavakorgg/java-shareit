package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

import java.time.LocalDateTime;

public class RequestMapper {
    public static ItemRequest toModel(NewItemRequestDto requestCreateDto) {
        return ItemRequest.builder()
                .description(requestCreateDto.getDescription())
                .created(LocalDateTime.now())
                .build();
    }
    public static ItemRequestDto toDto(ItemRequest request) {
        return ItemRequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(request.getResponsesList().stream().map(ItemMapper::itemResponseToRequestDto).toList())
                .build();
    }
}
