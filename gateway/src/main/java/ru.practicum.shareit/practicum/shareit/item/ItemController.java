package ru.practicum.shareit.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.practicum.shareit.dto.NewCommentDto;
import ru.practicum.shareit.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.practicum.shareit.item.dto.UpdateItemDto;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody NewItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {

        return itemClient.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@PathVariable("itemId") Long itemId,
                                         @RequestBody UpdateItemDto itemDto,
                                         @RequestHeader(value = "X-Sharer-User-Id") Long userId) {

        return itemClient.update(itemId, itemDto, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable("itemId") Long itemId) {

        return itemClient.getById(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {

        return itemClient.getAllItemsByOwner(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("text") String searchText) {

        return itemClient.search(searchText);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable("itemId") long itemId,
                                                @RequestBody @Valid NewCommentDto newCommentDto) {
        return itemClient.createComment(newCommentDto, itemId, userId);
    }
}
