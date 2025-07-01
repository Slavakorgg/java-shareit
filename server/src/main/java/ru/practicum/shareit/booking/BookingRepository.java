package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_Id(long bookerId);

    List<Booking> findByItemOwner_Id(long ownerId);

    @Query("SELECT b FROM Booking b " +
            " WHERE b.booker.id = :bookerId " +
            " AND b.start <= :now " +
            " AND b.end >= :now " +
            " AND b.status = ru.practicum.shareit.booking.BookingStatus.APPROVED")
    List<Booking> findCurrentByBooker(@Param("bookerId") long bookerId, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b " +
            " WHERE b.item.owner.id = :ownerId " +
            " AND b.start <= :now " +
            " AND b.end >= :now " +
            " AND b.status = ru.practicum.shareit.booking.BookingStatus.APPROVED")
    List<Booking> findCurrentByOwner(@Param("ownerId") long ownerId, @Param("now") LocalDateTime now);

    List<Booking> findByBooker_IdAndEndBefore(long bookerId, LocalDateTime now);

    List<Booking> findByItemOwner_IdAndEndBefore(long ownerId, LocalDateTime now);

    List<Booking> findByBooker_IdAndStartAfter(long bookerId, LocalDateTime now);

    List<Booking> findByItemOwner_IdAndStartAfter(long ownerId, LocalDateTime now);

    List<Booking> findByBooker_IdAndStatus(long bookerId, BookingStatus status);

    List<Booking> findByItemOwner_IdAndStatus(long ownerId, BookingStatus status);

    List<Booking> findByItem_idAndStatus(long itemId, BookingStatus status);

    Optional<Booking> findByItem_idAndBooker_id(long itemId, long bookerId);
}
