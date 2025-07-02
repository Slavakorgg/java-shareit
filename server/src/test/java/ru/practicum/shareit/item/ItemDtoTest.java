package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;


import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void testSerialize() throws Exception {
        UserDto ownerDto = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("new_item")
                .description("new_description")
                .available(true)
                .owner(ownerDto)
                .requestId(null)
                .build();

        assertThat(json.write(itemDto)).hasJsonPathNumberValue("$.id");
        assertThat(json.write(itemDto)).hasJsonPathStringValue("$.name");
        assertThat(json.write(itemDto)).hasJsonPathStringValue("$.description");
        assertThat(json.write(itemDto)).hasJsonPathBooleanValue("$.available");
        assertThat(json.write(itemDto)).hasJsonPathMapValue("$.owner");


    }

    @Test
    void testDeserialize() throws Exception {
        String content = "{"
                + "\"id\": 1,"
                + "\"name\": \"new_item\","
                + "\"description\": \"new_description\","
                + "\"available\": true,"
                + "\"owner\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"John\","
                + "  \"email\": \"john@example.com\""
                + "},"
                + "\"requestId\": \"null\""
                + "}";


        ItemDto itemDto = json.parseObject(content);

        assertThat(itemDto.getId()).isEqualTo(1L);
        assertThat(itemDto.getName()).isEqualTo("new_item");
        assertThat(itemDto.getDescription()).isEqualTo("new_description");
        assertThat(itemDto.getAvailable()).isTrue();
        assertThat(itemDto.getOwner().getId()).isEqualTo(1L);
        assertThat(itemDto.getOwner().getName()).isEqualTo("John");
        assertThat(itemDto.getOwner().getEmail()).isEqualTo("john@example.com");


    }

}