package ru.practicum.shareit.item;


import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;

import java.util.List;


public class ItemMapper {
    public static ItemDto mapToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.isAvailable())
                .owner(UserMapper.mapToUserDto(item.getOwner()))
                .build();
    }

    public static Item mapToNewItem(NewItemDto newItemDto, User owner) {
        return Item.builder()
                .name(newItemDto.getName())
                .description(newItemDto.getDescription())
                .available(newItemDto.getAvailable())
                .owner(owner)
                .request(newItemDto.getRequest())
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
                .build();
        if (!bookings.isEmpty()) {
            itemWithCommentsDto.setLastBooking(BookingMapper.mapToBookingDto(bookings.getLast()));
            if (bookings.size() > 1) {
                itemWithCommentsDto.setNextBooking(BookingMapper.mapToBookingDto(bookings.get(bookings.size() - 2)));
            }
        }
        return itemWithCommentsDto;
    }
}

