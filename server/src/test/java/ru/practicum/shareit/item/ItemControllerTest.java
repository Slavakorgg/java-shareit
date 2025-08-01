package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;


import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long userId = 1L;
    private final Long itemId = 1L;

    @Test
    void getAllItemsFromUserList() throws Exception {
        ItemDto item = new ItemDto();
        item.setId(itemId);
        item.setName("Drill");

        Mockito.when(itemService.getAllItemsByOwner(userId)).thenReturn(List.of(item));

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemId))
                .andExpect(jsonPath("$[0].name").value("Drill"));
    }

    @Test
    void getItemInfo() throws Exception {
        ItemWithCommentsDto item = new ItemWithCommentsDto();
        item.setId(itemId);
        item.setName("Drill");

        Mockito.when(itemService.get(itemId)).thenReturn(item);

        mockMvc.perform(get("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("Drill"));
    }

    @Test
    void addNewItem() throws Exception {
        NewItemDto createDto = new NewItemDto();
        createDto.setName("Drill");

        ItemDto responseDto = new ItemDto();
        responseDto.setId(itemId);
        responseDto.setName("Drill");

        Mockito.when(itemService.create(any(), eq(userId))).thenReturn(responseDto);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("Drill"));
    }

    @Test
    void updateItem() throws Exception {
        UpdateItemDto updateDto = new UpdateItemDto();
        updateDto.setName("Updated Drill");

        ItemDto responseDto = new ItemDto();
        responseDto.setId(itemId);
        responseDto.setName("Updated Drill");

        Mockito.when(itemService.update(eq(itemId), any(), eq(userId))).thenReturn(responseDto);

        mockMvc.perform(patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("Updated Drill"));
    }

    @Test
    void searchItems() throws Exception {
        ItemDto item = new ItemDto();
        item.setId(itemId);
        item.setName("Drill");

        Mockito.when(itemService.search("drill")).thenReturn(List.of(item));

        mockMvc.perform(get("/items/search")
                        .param("text", "drill")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemId))
                .andExpect(jsonPath("$[0].name").value("Drill"));
    }

    @Test
    void addComment() throws Exception {
        NewCommentDto createDto = new NewCommentDto();
        createDto.setText("Nice item!");

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("Nice item!");

        Mockito.when(itemService.createComment(any(), eq(itemId), eq(userId)))
                .thenReturn(commentDto);

        mockMvc.perform(post("/items/{itemId}/comment", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("Nice item!"));
    }
}