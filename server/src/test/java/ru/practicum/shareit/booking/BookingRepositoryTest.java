package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.ItemRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
    void findAllByOwnerIdAndEndingBeforeTest() {
        bookingDataList = bookingRepository.findByItemOwner_IdAndEndBefore(1, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.size());
    }

    @Test
    void findAllByBookerIdAndStartAfterTest() {
        bookingDataList = bookingRepository.findByBooker_IdAndStartAfter(2, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(0, bookingDataList.size());
    }

    @Test
    void findAllByOwnerIdAndStartAfterTest() {
        bookingDataList = bookingRepository.findByItemOwner_IdAndStartAfter(2, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(0, bookingDataList.size());
    }

    @Test
    void findCurrentByOwnerTest() {
        bookingDataList = bookingRepository.findCurrentByOwner(1, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);

    }

    @Test
    void findCurrentByBookerTest() {
        bookingDataList = bookingRepository.findCurrentByBooker(1, LocalDateTime.now());

        Assertions.assertNotNull(bookingDataList);

    }

    @Test
    void findAllByBookerIdAndStatusTest() {
        bookingDataList = bookingRepository.findByBooker_IdAndStatus(1, BookingStatus.WAITING);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.size());
    }

    @Test
    void findAllByOwnerIdAndStatusTest() {
        bookingDataList = bookingRepository.findByItemOwner_IdAndStatus(3, BookingStatus.WAITING);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.size());
    }

    @Test
    void findAllByItemIdAndStatusTest() {
        bookingDataList = bookingRepository.findByItem_idAndStatus(3, BookingStatus.WAITING);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.size());
    }

    @Test
    void findAllByOwnerIdTest() {
        bookingDataList = bookingRepository.findByItemOwner_Id(2);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.size());
    }

    @Test
    void findAllByItemIdAndBookerId() {
        Optional<Booking> bookingDataList = bookingRepository.findByItem_idAndBooker_id(3, 2);

        Assertions.assertNotNull(bookingDataList);
        Assertions.assertEquals(1, bookingDataList.stream().toList().size());
    }
}