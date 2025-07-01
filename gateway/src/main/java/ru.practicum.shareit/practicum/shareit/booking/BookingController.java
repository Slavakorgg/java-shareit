package ru.practicum.shareit.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.practicum.shareit.booking.dto.NewBookingDto;


/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody @Valid NewBookingDto newBookingDto, @RequestHeader("X-Sharer-User-Id") @NotNull long userId) {
        return bookingClient.create(newBookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> approveBooking(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Long bookingId, @RequestParam Boolean approved) {
        return bookingClient.approveBooking(userId, bookingId, approved);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findByBooker(@RequestHeader("X-Sharer-User-Id") @NotNull long userId,
                                               @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingClient.findByBooker(userId, state);
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findByOwner(@RequestHeader("X-Sharer-User-Id") @NotNull long userId,
                                              @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingClient.findByOwner(userId, state);
    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") @NotNull long userId, @PathVariable @NotNull long bookingId) {
        return bookingClient.findById(userId, bookingId);
    }
}
