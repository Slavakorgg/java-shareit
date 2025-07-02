package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(NewItemRequestDto newItemRequestDto, long userId);

    List<ItemRequestDto> getByUser(long id);

    ItemRequestDto getById(long id);
}
