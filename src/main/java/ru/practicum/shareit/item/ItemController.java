package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("==> Creating item: {}", itemDto);
        ItemDto item = itemService.create(itemDto, userId);
        log.info("<== Creating item: {}", item);
        return item;
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable("itemId") Long itemId, @RequestBody ItemDto itemDto, @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        log.info("==> Updating item: {}", itemDto);
        ItemDto item = itemService.update(itemId, itemDto, userId);
        log.info("==> Updating item: {}", item);
        return item;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId") Long itemId) {
        log.info("==> get item by id: {}", itemId);
        ItemDto item = itemService.getItem(itemId);
        log.info("<== get item by id: {}", item);
        return item;
    }

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("==> get user items by user id: {}", userId);
        List<ItemDto> items = itemService.getAllItemsByOwner(userId);
        log.info("<== get user items by user id: {}", userId);
        return items;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam("text") String searchText) {
        log.info("==> search items: {}", searchText);
        List<ItemDto> items = itemService.search(searchText);
        log.info("<== search items: {}", searchText);
        return items;
    }
}
