package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT * FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.item_id = ?1 " +
            "AND b.start < ?2 " +
            "AND b.booking_status = 'APPROVED' " +
            "ORDER BY b.start DESC LIMIT 1 ", nativeQuery = true)
    Optional<Booking> getLastBooking(Integer itemId, LocalDateTime currentTime);

    @Query(value = "SELECT * FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.item_id = ?1 " +
            "AND b.start > ?2 " +
            "AND b.booking_status = 'APPROVED' " +
            "ORDER BY b.start ASC LIMIT 1 ", nativeQuery = true)
    Optional<Booking> getNextBooking(Integer itemId, LocalDateTime currentTime);
}
