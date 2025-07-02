package ru.practicum.shareit.item;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    @Test
    void testSerialize() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name("item")
                .description("description")
                .available(true)
                .build();
        String instant = "2024-07-01T12:30:00Z";
        Instant created = Instant.parse(instant);

        CommentDto dto = CommentDto.builder()
                .id(10L)
                .text("text_test")
                .item(item)
                .authorName("Alice")
                .created(created)
                .build();

        assertThat(json.write(dto))
                .extractingJsonPathNumberValue("$.id").isEqualTo(10);
        assertThat(json.write(dto))
                .extractingJsonPathStringValue("$.text").isEqualTo("text_test");
        assertThat(json.write(dto))
                .extractingJsonPathStringValue("$.authorName").isEqualTo("Alice");
        assertThat(json.write(dto))
                .extractingJsonPathStringValue("$.created").isEqualTo("2024-07-01T12:30:00Z");
    }

    @Test
    void testDeserialize() throws Exception {

        String content = "{"
                + "\"id\": 10,"
                + "\"text\": \"text_test\","
                + "\"item\": {"
                + "  \"id\": 1,"
                + "  \"name\": \"item\","
                + "  \"description\": \"description\","
                + "  \"available\": true"
                + "},"
                + "\"authorName\": \"Bob\","
                + "\"created\": \"2024-07-01T12:30:00Z\""
                + "}";

        CommentDto dto = json.parseObject(content);

        assertThat(dto.getId()).isEqualTo(10);
        assertThat(dto.getText()).isEqualTo("text_test");
        assertThat(dto.getItem().getId()).isEqualTo(1);
        assertThat(dto.getAuthorName()).isEqualTo("Bob");
        assertThat(dto.getCreated()).isEqualTo("2024-07-01T12:30:00Z");
    }
}