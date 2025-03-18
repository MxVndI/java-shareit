package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingDto;

public interface BookingService {

    BookingDto addBooking(BookingDto bookingDto, Integer ownerId);

    BookingDto changeBookingStatus(Integer bookingId, Boolean approved, Integer ownerId);

    BookingDto getBookingById(Integer bookingId, Integer ownerId);

}
