package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.NewCommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.exception.NoAccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(NewItemDto newItemDto, long userId) {
        log.debug("==> Creating item: {}", newItemDto);
        User owner = UserMapper.mapToUser(userService.getById(userId));
        Item item = ItemMapper.mapToNewItem(newItemDto, owner);
        itemRepository.save(item);
        log.debug("<== Creating item: {}", newItemDto);
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public ItemDto update(long itemId, UpdateItemDto itemDto, long userID) {
        log.debug("==> Updating item: {}", itemDto);
        Item oldItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

        if (userID == oldItem.getOwner().getId()) {
            Item updateItem = ItemMapper.mapToUpdateItemDto(oldItem, itemDto);
            itemRepository.save(updateItem);
            log.debug("<== Updating item: {}", itemDto);
            return ItemMapper.mapToItemDto(updateItem);
        }
        throw new NotFoundException("У вас недостаточно прав для обновления вещи");
    }


    @Override
    public ItemWithCommentsDto get(long itemId) {
        log.debug("==> get item by id: {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        List<Booking> bookings = bookingRepository.findByItem_idAndStatus(itemId, BookingStatus.WAITING);
        List<Comment> comments = commentRepository.findByItem_id(itemId);
        ItemWithCommentsDto itemWithCommentsDto = ItemMapper.mapToItemWithCommentsDto(item, bookings, comments);
        log.debug("<== get item by id: {}", item);
        return itemWithCommentsDto;
    }

    @Override
    public List<ItemDto> getAllItemsByOwner(long ownerId) {
        log.debug("==> get user items by user id: {}", ownerId);
        return itemRepository.findByOwner(UserMapper.mapToUser(userService.getById(ownerId)))
                .stream()
                .map(ItemMapper::mapToItemDto)
                .toList();


    }

    @Override
    public List<ItemDto> search(String searchText) {
        log.debug("==> search items: {}", searchText);
        List<ItemDto> foundItems = new ArrayList<>();
        if (searchText.isEmpty() || searchText.isBlank() || searchText == null) {
            return foundItems;
        }
        foundItems = itemRepository.searchItem(searchText)
                .stream()
                .map(ItemMapper::mapToItemDto)
                .toList();
        log.debug("<== search items: {}", searchText);
        return foundItems;
    }

    @Override
    public CommentDto createComment(NewCommentDto newCommentDto, long itemId, long userId) {
        log.debug("==> create comment for item: {}", itemId);
        Booking booking = bookingRepository.findByItem_idAndBooker_id(itemId, userId)
                .orElseThrow(() -> new NotFoundException("Бронирование для предмета не найдено, невозможно добавить комментарий"));
        if (booking.getEnd().isAfter(LocalDateTime.now())) {
            throw new NoAccessException("Срок бронирования предмета еще не закончился");
        }
        User commentAuthor = UserMapper.mapToUser(userService.getById(userId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        Comment comment = CommentMapper.mapToNewComment(newCommentDto, item, commentAuthor);
        commentRepository.save(comment);
        log.debug("<== create comment for item: {}", itemId);
        return CommentMapper.mapToCommentDto(comment);
    }

}
