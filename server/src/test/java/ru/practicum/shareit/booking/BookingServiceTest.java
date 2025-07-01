package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.exception.NoAccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTest {
    private final BookingService bookingService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private NewBookingDto newBookingDto;

    @Test
    void createBookingTest() {
        newBookingDto = new NewBookingDto(LocalDateTime.of(2026, 4, 5, 0, 0, 0),
                LocalDateTime.of(2026, 4, 7, 0, 0, 0), 1L);

        BookingDto bookingDto = bookingService.create(newBookingDto, 2);

        Assertions.assertNotNull(bookingDto);
        Assertions.assertEquals(5, bookingDto.getId());
        Assertions.assertEquals(1, bookingDto.getItem().getId());
        Assertions.assertEquals(2, bookingDto.getBooker().getId());
        Assertions.assertEquals(BookingStatus.WAITING, bookingDto.getStatus());
    }

    @Test
    void createBookingWithWrongBookerIdTest() {
        newBookingDto = new NewBookingDto(LocalDateTime.of(2026, 4, 5, 0, 0, 0),
                LocalDateTime.of(2026, 4, 7, 0, 0, 0), 1L);

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> bookingService.create(newBookingDto, 1000L)
        );

        Assertions.assertEquals(e.getMessage(), "Пользователь не найден");
    }

    @Test
    void createBookingWithWrongItemIdTest() {
        newBookingDto = new NewBookingDto(LocalDateTime.of(2026, 4, 5, 0, 0, 0),
                LocalDateTime.of(2026, 4, 7, 0, 0, 0), 1000L);

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> bookingService.create(newBookingDto, 1L)
        );

        Assertions.assertEquals(e.getMessage(), "Вещь не найдена");
    }



    @Test
    void createBookingWithNotAvailableItemTest() {
        newBookingDto = new NewBookingDto(LocalDateTime.of(2026, 4, 5, 0, 0, 0),
                LocalDateTime.of(2026, 4, 7, 0, 0, 0), 4L);

        RuntimeException e = assertThrows(RuntimeException.class,
                () -> bookingService.create(newBookingDto, 2)
        );

        Assertions.assertEquals(e.getMessage(), "Вещь недоступна для бронирования");

    }

    @Test
    void bookingApproveTest() {
        BookingDto bookingDto = bookingService.approveBooking(3, 3, true);

        Assertions.assertNotNull(bookingDto);
        Assertions.assertEquals(BookingStatus.APPROVED, bookingDto.getStatus());
    }



    @Test
    void bookingApproveWhereUserIsNotUserTest() {
        RuntimeException e = assertThrows(RuntimeException.class,
                () -> bookingService.approveBooking(1, 3, true)
        );

        Assertions.assertEquals(e.getMessage(), "Только владелец вещи имеет доступ к изменению статуса бронирования");

    }

    @Test
    void getBookingByIdTest() {
        BookingDto bookingDto1 = bookingService.findById(3, 3);

        Assertions.assertNotNull(bookingDto1);
        Assertions.assertEquals(BookingStatus.APPROVED, bookingDto1.getStatus());
    }

    @Test
    void getBookingByWrongIdTest() {
        NoAccessException e = assertThrows(NoAccessException.class,
                () -> bookingService.findById(1000, 2)
        );

        Assertions.assertEquals(e.getMessage(), "No access to booking");
    }


    @Test
    void getAllUsersBookingsTest() {
        List<BookingDto> bookingDtoList = bookingService.findByBooker( 2,BookingState.ALL);

        Assertions.assertNotNull(bookingDtoList);
        Assertions.assertEquals(3, bookingDtoList.size());

        List<BookingDto> bookingDtoList1 = bookingService.findByBooker( 2,BookingState.PAST);

        Assertions.assertNotNull(bookingDtoList1);
        Assertions.assertEquals(2, bookingDtoList1.size());

        List<BookingDto> bookingDtoList2 = bookingService.findByBooker( 2,BookingState.FUTURE);

        Assertions.assertNotNull(bookingDtoList2);
        Assertions.assertEquals(0, bookingDtoList2.size());

        List<BookingDto> bookingDtoList3 = bookingService.findByBooker( 2,BookingState.CURRENT);

        Assertions.assertNotNull(bookingDtoList3);
        Assertions.assertEquals(0, bookingDtoList3.size());

        List<BookingDto> bookingDtoList4 = bookingService.findByBooker( 2,BookingState.WAITING);

        Assertions.assertNotNull(bookingDtoList3);
        Assertions.assertEquals(0, bookingDtoList4.size());

        List<BookingDto> bookingDtoList5 = bookingService.findByBooker( 2,BookingState.REJECTED);

        Assertions.assertNotNull(bookingDtoList3);
        Assertions.assertEquals(1, bookingDtoList5.size());
    }

    @Test
    void getAllUsersBookingsWithWrongStateTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> bookingService.findByBooker( 2,BookingState.valueOf("asd"))
        );

        Assertions.assertEquals(e.getMessage(), "No enum constant ru.practicum.shareit.booking.BookingState.asd");
    }

    @Test
    void getAllBookingsForAllUsersItemsTest() {
        List<BookingDto> bookingDtoList = bookingService.findByOwner( 3,BookingState.ALL);

        Assertions.assertNotNull(bookingDtoList);
        Assertions.assertEquals(2, bookingDtoList.size());

        List<BookingDto> bookingDtoList1 = bookingService.findByOwner( 3,BookingState.PAST);

        Assertions.assertNotNull(bookingDtoList1);
        Assertions.assertEquals(1, bookingDtoList1.size());

        List<BookingDto> bookingDtoList2 = bookingService.findByOwner( 3,BookingState.FUTURE);

        Assertions.assertNotNull(bookingDtoList2);
        Assertions.assertEquals(1, bookingDtoList2.size());

        List<BookingDto> bookingDtoList3 = bookingService.findByOwner( 3,BookingState.CURRENT);

        Assertions.assertNotNull(bookingDtoList3);
        Assertions.assertEquals(0, bookingDtoList3.size());

        List<BookingDto> bookingDtoList4 = bookingService.findByOwner( 3,BookingState.WAITING);

        Assertions.assertNotNull(bookingDtoList3);
        Assertions.assertEquals(1, bookingDtoList4.size());

        List<BookingDto> bookingDtoList5 = bookingService.findByOwner( 3,BookingState.REJECTED);

        Assertions.assertNotNull(bookingDtoList3);
        Assertions.assertEquals(0, bookingDtoList5.size());
    }

    @Test
    void getAllBookingsForAllUsersItemsWithWrongStatusTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> bookingService.findByOwner( 3,BookingState.valueOf("asd"))
        );

        Assertions.assertEquals(e.getMessage(), "No enum constant ru.practicum.shareit.booking.BookingState.asd");
    }

}