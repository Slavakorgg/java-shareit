package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;


import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestServiceTest {
    private final ItemRequestServiseImpl requestService;

    @Test
    void postRequestTest() {
        NewItemRequestDto newItemRequestDto = NewItemRequestDto.builder()
                .description("request_test_description")
                .build();

        ItemRequestDto itemRequestDto = requestService.create(newItemRequestDto, 1);

        Assertions.assertNotNull(itemRequestDto);
        Assertions.assertEquals(2, itemRequestDto.getId());
        Assertions.assertEquals("request_test_description", itemRequestDto.getDescription());
    }

    @Test
    void postRequestWithWrongUserIdTest() {
        NewItemRequestDto newItemRequestDto = NewItemRequestDto.builder()
                .description("request_test_description")
                .build();

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> requestService.create(newItemRequestDto, 100)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь с id = 100 не найден");
    }

    @Test
    void getRequestByIdTest() {
        ItemRequestDto requestDto = requestService.getById(1);

        Assertions.assertNotNull(requestDto);
        Assertions.assertEquals("request_description", requestDto.getDescription());
    }

    @Test
    void getRequestByWrongIdTest() {
        NotFoundException e = assertThrows(NotFoundException.class,
                () -> requestService.getById(100)
        );

        Assertions.assertEquals(e.getMessage(), "Запрос вещи с id = 100 не найден");
    }

}