package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;

public interface BookingService {
    BookingDto create(NewBookingDto newBookingDto, long bookerId);

    BookingDto approveBooking(long userId, long bookingId, boolean approved);

    Page<BookingDto> findByBooker(long bookerId, BookingState state, Pageable pageable);

    Page<BookingDto> findByOwner(long ownerId, BookingState state, Pageable pageable);

    BookingDto findById(long userId, long bookingId);
}
