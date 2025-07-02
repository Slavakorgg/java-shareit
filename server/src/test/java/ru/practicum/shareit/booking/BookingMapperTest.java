package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperTest {

    @Test
    void mapToBookingDtoTest() {
        User user = new User(1L, "Tom", "tom@mail.ru");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.now();
        Item item = new Item(1L, "test-item", "test-description", true, user, null);

        Booking booking = new Booking(1, localDateTime, localDateTime2, item, user, BookingStatus.WAITING);
        BookingMapper mapper = new BookingMapper();

        assertEquals(mapper.mapToBookingDto(booking).getStart(), localDateTime);
        assertEquals(mapper.mapToBookingDto(booking).getEnd(), localDateTime2);
        assertEquals(mapper.mapToBookingDto(booking).getStatus(), BookingStatus.WAITING);
        assertEquals(mapper.mapToBookingDto(booking).getId(), 1);
        assertEquals(mapper.mapToBookingDto(booking).getBooker().getId(), user.getId());
        assertEquals(mapper.mapToBookingDto(booking).getItem().getId(), item.getId());
    }

    @Test
    void mapToNewBookingDto() {
        User user = new User(1L, "Tom", "tom@mail.ru");
        Item item = new Item(1L, "test-item", "test-description", true, user, null);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.now();
        NewBookingDto newBookingDto = NewBookingDto.builder()
                .start(localDateTime)
                .end(localDateTime2)
                .itemId(1L)
                .build();
        BookingMapper mapper = new BookingMapper();
        assertEquals(mapper.mapToNewBookingDto(newBookingDto, user, item).getStart(), localDateTime);
        assertEquals(mapper.mapToNewBookingDto(newBookingDto, user, item).getEnd(), localDateTime2);
        assertEquals(mapper.mapToNewBookingDto(newBookingDto, user, item).getStatus(), BookingStatus.WAITING);
        assertEquals(mapper.mapToNewBookingDto(newBookingDto, user, item).getBooker().getId(), user.getId());
        assertEquals(mapper.mapToNewBookingDto(newBookingDto, user, item).getItem().getId(), item.getId());


    }
}
