package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT b.booking_id AS booking_id, b.item_id AS booking_item_id," +
            " b.start, b.ends, b.booking_status, b.booker_id," +
            "i.item_id AS item_id, i.name, i.description " +
            "FROM bookings AS b " +
            "JOIN items AS i ON i.item_id = b.item_id " +
            "WHERE b.item_id = ?1 " +
            "AND b.start < ?2 " +
            "AND b.booking_status = 'APPROVED' " +
            "ORDER BY b.start DESC LIMIT 1", nativeQuery = true)
    Optional<Booking> getLastBooking(Integer itemId, LocalDateTime currentTime);

    @Query(value = "SELECT b.booking_id AS booking_id, b.item_id AS booking_item_id, " +
            "b.start, b.ends, b.booking_status, b.booker_id, " +
            "i.item_id AS item_id, i.name, i.description " +
            "FROM bookings AS b " +
            "JOIN items AS i ON i.item_id = b.item_id " +
            "WHERE b.item_id = ?1 " +
            "AND b.start > ?2 " +
            "AND b.booking_status = 'APPROVED' " +
            "ORDER BY b.start ASC LIMIT 1", nativeQuery = true)
    Optional<Booking> getNextBooking(Integer itemId, LocalDateTime currentTime);

    Optional<Booking> findByBookerIdAndItemId(Integer bookerId, Integer itemId);

    List<Booking> findAllByBookerId(Integer bookerId);
}

