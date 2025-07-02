package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.NewCommentDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceImplTest {
    private final ItemServiceImpl itemService;

    @Test
    void addNewItemTest() {
        NewItemDto newItemDto = NewItemDto.builder()
                .name("new_item_5")
                .description("new_item_5_description")
                .available(true)
                .build();

        ItemDto itemDto = itemService.create(newItemDto, 1);

        Assertions.assertNotNull(itemDto);
        assertEquals(5, itemDto.getId());
        assertEquals("new_item_5", itemDto.getName());
        assertEquals("new_item_5_description", itemDto.getDescription());
        assertEquals(1, itemDto.getOwner().getId());

    }

    @Test
    void addNewItemWithWrongOwnerTest() {
        NewItemDto newItemDto = NewItemDto.builder()
                .name("new_item_5")
                .description("new_item_5_description")
                .available(true)
                .build();

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> itemService.create(newItemDto, 1000)
        );

        assertEquals(e.getMessage(), "Пользователь с id = 1000 не найден");
    }

    @Test
    void updateItemTest() {
        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .name("new_item_test")
                .build();

        ItemDto itemDto = itemService.update(1, updateItemDto, 1);

        Assertions.assertNotNull(itemDto);
        assertEquals("new_item_test", itemDto.getName());
    }

    @Test
    void updateItemWithWrongIdTest() {
        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .name("new_item_test")
                .build();

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> itemService.update(1000, updateItemDto, 2)
        );

        assertEquals(e.getMessage(), "Предмет не найден");
    }

    @Test
    void updateItemWithUserIsNotOwnerTest() {
        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .name("new_item_test")
                .build();

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> itemService.update(1, updateItemDto, 3)
        );

        assertEquals(e.getMessage(), "У вас недостаточно прав для обновления вещи");
    }

    @Test
    void getItemByIdTest() {
        ItemWithCommentsDto itemDto = itemService.get(1L);

        Assertions.assertNotNull(itemDto);
        assertEquals("new_item", itemDto.getName());
        assertEquals("description_new_item", itemDto.getDescription());
    }

    @Test
    void getItemsByOwner() {
        List<ItemDto> itemDtoList = itemService.getAllItemsByOwner(3L);

        Assertions.assertNotNull(itemDtoList);
        assertEquals(3, itemDtoList.size());
    }

    @Test
    void searchItemsTest() {
        List<ItemDto> itemDtoList = itemService.search("item");

        Assertions.assertNotNull(itemDtoList);
        assertEquals(3, itemDtoList.size());
    }

    @Test
    void addNewCommentTest() {
        NewCommentDto newCommentDto = NewCommentDto.builder()
                .text("comment_test_text").build();

        CommentDto commentDto = itemService.createComment(newCommentDto, 3, 2);

        Assertions.assertNotNull(commentDto);
        assertEquals(2, commentDto.getId());
        assertEquals("comment_test_text", commentDto.getText());
        assertEquals("booker", commentDto.getAuthorName());
    }

    @Test
    void addNewCommentWithWrongBookerIdTest() {
        NewCommentDto newCommentDto = NewCommentDto.builder()
                .text("comment_test_text").build();

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> itemService.createComment(newCommentDto, 3, 3)
        );

        assertEquals(e.getMessage(), "Бронирование для предмета не найдено, невозможно добавить комментарий");

    }
}