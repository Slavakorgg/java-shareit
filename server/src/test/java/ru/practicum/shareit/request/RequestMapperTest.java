package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestMapperTest {

    @Test
    void newRequestDtoMapperTest() {

        NewItemRequestDto newItemRequestDto = new NewItemRequestDto("test-description", LocalDateTime.now());
        RequestMapper requestMapper = new RequestMapper();
        assertEquals(requestMapper.toModel(newItemRequestDto).getDescription(), "test-description");


    }

    @Test
    void toDtoMapperTest() {

        ItemRequest itemRequestDto = new ItemRequest();
        itemRequestDto.setDescription("test-description");
        RequestMapper requestMapper = new RequestMapper();
        assertEquals(requestMapper.toDto(itemRequestDto).getDescription(), "test-description");


    }
}
