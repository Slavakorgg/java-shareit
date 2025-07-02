package ru.practicum.shareit.practicum.shareit.booking;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.practicum.shareit.client.BaseClient;

import java.util.Map;


public class BookingClient extends BaseClient {

    public BookingClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getBookings(long userId, BookingState state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }


    public ResponseEntity<Object> bookItem(long userId, BookItemRequestDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> create(NewBookingDto newBookingDto, long userId) {
        return post("", userId, newBookingDto);
    }


    public ResponseEntity<Object> approveBooking(long userId, long bookingId, boolean approved) {
        Map<String, Object> uriVariables = Map.of("bookingId", bookingId, "approved", approved);
        return patch("/" + bookingId + "?approved=" + approved, userId, approved);
    }

    public ResponseEntity<Object> findById(long userId, long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> findByBooker(long userId, BookingState state) {
        Map<String, Object> parameters = Map.of(
                "state", state.name()
        );
        return get("?state={state}", userId, parameters);
    }

    public ResponseEntity<Object> findByOwner(long userId, BookingState state) {
        Map<String, Object> parameters = Map.of(
                "state", state.name()
        );
        return get("/owner", userId, parameters);
    }
}
