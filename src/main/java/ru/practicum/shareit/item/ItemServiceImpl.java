package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        User owner = userRepository.findById(userId);
        if (owner == null) {
            throw new NotFoundException("Владелец вещи не найден");
        }
        Item item = itemMapper.toItem(itemDto);
        item.setOwner(owner);
        itemRepository.save(item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long userID) {
        Item item = itemRepository.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Вещь не найдена");
        }
        if (!Objects.equals(userID, item.getOwner().getId())) {
            throw new NotFoundException("У вас недостаточно прав для обновления вещи");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        itemRepository.save(item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Предмет не найден");
        }
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemsByOwner(Long ownerId) {
        List<Item> items = itemRepository.findByOwnerId(ownerId);
        return items.stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String searchText) {
        List<Item> foundItems = itemRepository.search(searchText);
        return foundItems.stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }
}
