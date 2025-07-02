package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemMapperTest {

    @Test
    void mapToDtoTest() {
        User user = new User(1L, "test-name", "test@mail.ru");
        Item item = new Item(1L, "Drill", "test-description", true, user, null);
        ItemMapper mapper = new ItemMapper();
        assertEquals(mapper.mapToItemDto(item).getName(), "Drill");
        assertEquals(mapper.mapToItemDto(item).getDescription(), "test-description");
        assertEquals(mapper.mapToItemDto(item).getAvailable(), true);
        assertEquals(mapper.mapToItemDto(item).getOwner().getId(), user.getId());
    }

    @Test
    void mapToNewItemTest() {
        User user = new User(1L, "test-name", "test@mail.ru");
        NewItemDto itemDto = NewItemDto.builder()
                .name("Drill")
                .description("test-description")
                .available(true)
                .owner(user)
                .build();
        ItemMapper mapper = new ItemMapper();
        assertEquals(mapper.mapToNewItem(itemDto, user).getName(), "Drill");
        assertEquals(mapper.mapToNewItem(itemDto, user).getDescription(), "test-description");
        assertEquals(mapper.mapToNewItem(itemDto, user).isAvailable(), true);
        assertEquals(mapper.mapToNewItem(itemDto, user).getOwner().getId(), user.getId());

    }

    @Test
    void mapToUpdateIdemDtoTest() {
        User user = new User(1L, "test-name", "test@mail.ru");
        Item item = new Item(1L, "Drill", "test-description", true, user, null);
        UpdateItemDto itemDto = UpdateItemDto.builder()
                .name("update-name")
                .description("update-description")
                .build();
        ItemMapper mapper = new ItemMapper();
        assertEquals(mapper.mapToUpdateItemDto(item, itemDto).getName(), "update-name");
        assertEquals(mapper.mapToUpdateItemDto(item, itemDto).getDescription(), "update-description");
        assertEquals(mapper.mapToUpdateItemDto(item, itemDto).isAvailable(), true);
        assertEquals(mapper.mapToUpdateItemDto(item, itemDto).getOwner().getId(), user.getId());

    }
}
