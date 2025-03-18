package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingDto;

import java.util.List;

public interface BookingService {

    BookingDto addBooking(BookingDto bookingDto, Integer ownerId);

    BookingDto changeBookingStatus(Integer bookingId, Boolean approved, Integer ownerId);

    BookingDto getBookingById(Integer bookingId, Integer ownerId);

    List<BookingDto> getAllBookingsByOwner(Integer ownerId);

    List<BookingDto> getAllBookingsByBooker(Integer bookerId);
}
