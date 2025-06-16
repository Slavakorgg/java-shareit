package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<BookingDto> findByBooker(long bookerId, BookingState state, Pageable pageable) {
        if (!userExistById(bookerId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        LocalDateTime timeNow = LocalDateTime.now();
        Page<Booking> page = switch (state) {
            case CURRENT -> bookingRepository.findCurrentByBooker(bookerId, timeNow, pageable);
            case PAST -> bookingRepository.findByBooker_IdAndEndBefore(bookerId, timeNow, pageable);
            case FUTURE -> bookingRepository.findByBooker_IdAndStartAfter(bookerId, timeNow, pageable);
            case WAITING -> bookingRepository.findByBooker_IdAndStatus(bookerId, BookingStatus.WAITING, pageable);
            case REJECTED -> bookingRepository.findByBooker_IdAndStatus(bookerId, BookingStatus.REJECTED, pageable);
            case ALL -> bookingRepository.findByBooker_Id(bookerId, pageable);
            default -> throw new NotFoundException("Некорректный параметр state");
        };
        return page.map(BookingMapper::mapToBookingDto);
    }

    @Override
    public Page<BookingDto> findByOwner(long ownerId, BookingState state, Pageable pageable) {
        if (!userExistById(ownerId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        LocalDateTime timeNow = LocalDateTime.now();
        Page<Booking> page = switch (state) {
            case CURRENT -> bookingRepository.findCurrentByOwner(ownerId, timeNow, pageable);
            case PAST -> bookingRepository.findByItemOwner_IdAndEndBefore(ownerId, timeNow, pageable);
            case FUTURE -> bookingRepository.findByItemOwner_IdAndStartAfter(ownerId, timeNow, pageable);
            case WAITING -> bookingRepository.findByItemOwner_IdAndStatus(ownerId, BookingStatus.WAITING, pageable);
            case REJECTED -> bookingRepository.findByItemOwner_IdAndStatus(ownerId, BookingStatus.REJECTED, pageable);
            case ALL -> bookingRepository.findByItemOwner_Id(ownerId, pageable);
            default -> throw new NotFoundException("Некорректный параметр state");
        };
        return page.map(BookingMapper::mapToBookingDto);
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
