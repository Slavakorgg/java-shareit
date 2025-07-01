package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.List;

public interface BookingService {
    BookingDto create(NewBookingDto newBookingDto, long bookerId);

    BookingDto approveBooking(long userId, long bookingId, boolean approved);

    List<BookingDto> findByBooker(long bookerId, BookingState state);

    List<BookingDto> findByOwner(long ownerId, BookingState state);

    BookingDto findById(long userId, long bookingId);
}
