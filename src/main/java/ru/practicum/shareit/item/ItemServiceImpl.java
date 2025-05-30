package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        log.debug("==> Creating item: {}", itemDto);
        User owner = userRepository.findById(userId);
        if (owner == null) {
            throw new NotFoundException("Владелец вещи не найден");
        }
        Item item = itemMapper.toItem(itemDto);
        item.setOwner(owner);
        itemRepository.save(item);
        log.debug("<== Creating item: {}", item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long userID) {
        log.debug("==> Updating item: {}", itemDto);
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
        log.debug("==> Updating item: {}", item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto get(Long itemId) {
        log.debug("==> get item by id: {}", itemId);
        Item item = itemRepository.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Предмет не найден");
        }
        log.debug("<== get item by id: {}", item);
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemsByOwner(Long ownerId) {
        log.debug("==> get user items by user id: {}", ownerId);
        List<Item> items = itemRepository.findByOwnerId(ownerId);
        log.debug("<== get user items by user id: {}", ownerId);
        return items.stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String searchText) {
        log.debug("==> search items: {}", searchText);
        List<Item> foundItems = itemRepository.search(searchText);
        log.debug("<== search items: {}", searchText);
        return foundItems.stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }
}
