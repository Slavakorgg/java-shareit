package ru.practicum.shareit.item;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NoDataFoundException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ItemRepository {
    private final Map<Long, Item> items = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();
    private final ItemMapper itemMapper;

    public Item save(Item item) {
        validateItem(itemMapper.toItemDto(item));
        if (item.getId() == null) {
            item.setId(idCounter.incrementAndGet());
            items.put(item.getId(), item);
        } else {
            if (items.containsKey(item.getId())) {
                items.put(item.getId(), item);
            } else {
                throw new NotFoundException("Item не найден");
            }
        }
        return item;
    }

    public Item findById(Long id) {
        return items.get(id);
    }

    public List<Item> findByOwnerId(Long ownerId) {
        return items.values().stream().filter(i -> i.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        items.remove(id);
    }

    public List<Item> search(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyList();
        }

        return items.values()
                .stream()
                .filter(i -> i != null && i.isAvailable()
                        && (StringUtils.isNotEmpty(i.getName())
                        && i.getName().toLowerCase().contains(text.toLowerCase())
                        || StringUtils.isNotEmpty(i.getDescription())
                        && i.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    private void validateItem(ItemDto itemDto) {
        if (itemDto.getAvailable() == null || itemDto.getName().isBlank() || itemDto.getDescription() == null) {
            throw new NoDataFoundException("Некорректное добавление вещи. Проверьте заполненность необходимых полей");
        }

    }
}

