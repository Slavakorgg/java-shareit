package ru.practicum.shareit.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";
    private static final String BOOKING_ID_PATH = "/{bookingId}";
    private static final String BOOKING_PATH = BOOKING_ID_PATH + "?approved={approved}";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
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
        return post("",userId,newBookingDto);
    }

    /*public ResponseEntity<Object> approveBooking(long userId, long bookingId, boolean approved) {
        Map<String, Object> uriVariables = Map.of("bookingId",bookingId,"approved", approved);
        return patch(BOOKING_PATH,userId,uriVariables);
    }*/

    public ResponseEntity<Object> approveBooking(long userId, long bookingId, boolean approved) {
        Map<String, Object> uriVariables = Map.of("bookingId",bookingId,"approved", approved);
        return patch("/" + bookingId+"?approved="+approved, userId, approved);
    }

    public ResponseEntity<Object> findById(long userId, long bookingId) {
        return get("/" + bookingId,userId);
    }

    public ResponseEntity<Object> findByBooker(long userId, BookingState state) {
        Map<String,Object> parameters = Map.of(
                "state", state.name()
        );
        return get("?state={state}",userId,parameters);
    }

    public ResponseEntity<Object> findByOwner(long userId, BookingState state) {
        Map<String,Object> parameters = Map.of(
                "state", state.name()
        );
        return get("/owner", userId, parameters);
    }
}
