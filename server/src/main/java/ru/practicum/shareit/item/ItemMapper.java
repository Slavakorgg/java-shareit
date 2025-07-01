package ru.practicum.shareit.item;


import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;

import java.util.List;


public class ItemMapper {
    public static ItemDto mapToItemDto(Item item) {
        Long requestId = item.getRequest() == null ? null : item.getRequest().getId();
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .owner(UserMapper.mapToUserDto(item.getOwner()))
                .requestId(requestId)
                .build();
    }

    public static Item mapToNewItem(NewItemDto newItemDto, User owner) {
        ItemRequest request = newItemDto.getRequestId() == null ? null :ItemRequest.builder().id(newItemDto.getRequestId()).build();

        return Item.builder()
                .name(newItemDto.getName())
                .description(newItemDto.getDescription())
                .available(newItemDto.getAvailable())
                .owner(owner)
                .request(request)
                .build();
    }

    public static Item mapToUpdateItemDto(Item item, UpdateItemDto updateItemDto) {
        if (updateItemDto.hasName()) {
            item.setName(updateItemDto.getName());
        }
        if (updateItemDto.hasDescription()) {
            item.setDescription(updateItemDto.getDescription());
        }
        if (updateItemDto.hasAvailable()) {
            item.setAvailable(updateItemDto.getAvailable());
        }
        return item;

    }

    public static ItemWithCommentsDto mapToItemWithCommentsDto(Item item, List<Booking> bookings, List<Comment> comments) {
        ItemWithCommentsDto itemWithCommentsDto = ItemWithCommentsDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .comments(comments.stream().map(CommentMapper::mapToCommentDto).toList())
                .owner(UserMapper.mapToUserDto(item.getOwner()))
                .build();
        if (!bookings.isEmpty()) {
            itemWithCommentsDto.setLastBooking(BookingMapper.mapToBookingDto(bookings.getLast()));
            if (bookings.size() > 1) {
                itemWithCommentsDto.setNextBooking(BookingMapper.mapToBookingDto(bookings.get(bookings.size() - 2)));
            }
        }
        return itemWithCommentsDto;
    }
    public static ItemResponseToRequestDto itemResponseToRequestDto(Item item) {
        return ItemResponseToRequestDto.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(item.getOwner().getId())
                .build();
    }
}

