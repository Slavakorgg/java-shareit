package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.exception.NoAccessException;
import ru.practicum.shareit.exception.NotAvailableItemException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto create(NewBookingDto newBookingDto, long bookerId) {
        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(newBookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        if (!item.isAvailable()) {
            throw new NotAvailableItemException("Вещь недоступна для бронирования");

        }
        Booking booking = BookingMapper.mapToNewBookingDto(newBookingDto, booker, item);
        bookingRepository.save(booking);
        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public BookingDto approveBooking(long userId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        long ownerId = booking.getItem().getOwner().getId();
        if (userId != ownerId) {
            throw new NoAccessException("Только владелец вещи имеет доступ к изменению статуса бронирования");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> findByBooker(long bookerId, BookingState state) {
        if (!userExistById(bookerId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        LocalDateTime timeNow = LocalDateTime.now();
        List<Booking> bookings = switch (state) {
            case CURRENT -> bookingRepository.findCurrentByBooker(bookerId, timeNow);
            case PAST -> bookingRepository.findByBooker_IdAndEndBefore(bookerId, timeNow);
            case FUTURE -> bookingRepository.findByBooker_IdAndStartAfter(bookerId, timeNow);
            case WAITING -> bookingRepository.findByBooker_IdAndStatus(bookerId, BookingStatus.WAITING);
            case REJECTED -> bookingRepository.findByBooker_IdAndStatus(bookerId, BookingStatus.REJECTED);
            case ALL -> bookingRepository.findByBooker_Id(bookerId);
            default -> throw new NotFoundException("Некорректный параметр state");
        };
        return bookings.stream().map(BookingMapper::mapToBookingDto).toList();
    }

    @Override
    public List<BookingDto> findByOwner(long ownerId, BookingState state) {
        if (!userExistById(ownerId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        LocalDateTime timeNow = LocalDateTime.now();
        List<Booking> bookings = switch (state) {
            case CURRENT -> bookingRepository.findCurrentByOwner(ownerId, timeNow);
            case PAST -> bookingRepository.findByItemOwner_IdAndEndBefore(ownerId, timeNow);
            case FUTURE -> bookingRepository.findByItemOwner_IdAndStartAfter(ownerId, timeNow);
            case WAITING -> bookingRepository.findByItemOwner_IdAndStatus(ownerId, BookingStatus.WAITING);
            case REJECTED -> bookingRepository.findByItemOwner_IdAndStatus(ownerId, BookingStatus.REJECTED);
            case ALL -> bookingRepository.findByItemOwner_Id(ownerId);
            default -> throw new NotFoundException("Некорректный параметр state");
        };
        return bookings.stream().map(BookingMapper::mapToBookingDto).toList();
    }

    @Override
    public BookingDto findById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        long ownerId = booking.getItem().getOwner().getId();
        if (booking.getBooker().getId() != userId && ownerId != userId) {
            throw new NoAccessException("No access to booking");
        }
        return BookingMapper.mapToBookingDto(booking);
    }

    public boolean userExistById(long userId) {
        return userRepository.existsById(userId);
    }

}
