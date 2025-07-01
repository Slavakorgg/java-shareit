package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.ItemRepository;


import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
public class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ItemRepository itemRepository;

    private List<Booking> bookingDataList;


    @Test
    void findAllByBookerIdTest() {
        bookingDataList = bookingRepository.findByBooker_Id(2);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(3, bookingDataList.size());
    }

    @Test
    void findAllByBookerIdAndEndingBeforeTest() {
        bookingDataList = bookingRepository.findByBooker_IdAndEndBefore(2, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(2, bookingDataList.size());
    }

    @Test
    void findAllByBookerIdAndStartAfterTest() {
        bookingDataList = bookingRepository.findByBooker_IdAndStartAfter(2, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(0, bookingDataList.size());
    }

    @Test
    void findAllByBookerIdCurrentBookingTest() {
        bookingDataList = bookingRepository.findCurrentByOwner(1, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);

    }

    @Test
    void findAllByBookerIdAndStatusTest() {
        bookingDataList = bookingRepository.findByBooker_IdAndStatus(2, BookingStatus.WAITING);

        Assertions.assertTrue(bookingDataList.isEmpty());
    }

    @Test
    void findAllByOwnerIdTest() {
        bookingDataList = bookingRepository.findByItemOwner_Id(2);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.size());
    }
}