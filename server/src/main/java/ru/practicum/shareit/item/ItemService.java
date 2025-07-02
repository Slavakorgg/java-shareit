package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

import java.util.List;

public interface ItemService {

    ItemDto create(NewItemDto newItemDto, long userId);

    ItemDto update(long itemId, UpdateItemDto itemDto, long userID);

    ItemWithCommentsDto get(long itemId);

    List<ItemDto> getAllItemsByOwner(long ownerId);

    List<ItemDto> search(String searchText);

    CommentDto createComment(NewCommentDto newCommentDto, long itemId, long userId);
}
