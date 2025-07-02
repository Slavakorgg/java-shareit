package ru.practicum.shareit.practicum.shareit.item;


import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.practicum.shareit.dto.NewCommentDto;
import ru.practicum.shareit.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.practicum.shareit.item.dto.UpdateItemDto;


public class ItemClient extends BaseClient {


    public ItemClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> create(NewItemDto itemDto, Long userId) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> update(Long itemId, UpdateItemDto itemDto, Long userId) {
        return patch("/" + itemId, userId, itemDto);
    }

    public ResponseEntity<Object> getById(Long itemId) {
        return get("/" + itemId);
    }

    public ResponseEntity<Object> getAllItemsByOwner(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> search(String searchText) {
        return get("/search?text" + searchText);
    }

    public ResponseEntity<Object> createComment(NewCommentDto newCommentDto, long itemId, long userId) {
        return post("/" + itemId + "/comment", userId, newCommentDto);
    }
}
