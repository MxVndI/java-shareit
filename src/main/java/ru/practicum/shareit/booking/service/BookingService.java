package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoOut;

import java.util.List;

public interface BookingService {

    BookingDtoOut addBooking(BookingDto bookingDto, Integer ownerId);

    BookingDtoOut changeBookingStatus(Integer bookingId, Boolean approved, Integer ownerId);

    BookingDtoOut getBookingById(Integer bookingId, Integer ownerId);

    List<BookingDtoOut> getAllBookingsByOwner(Integer ownerId);

    List<BookingDtoOut> getAllBookingsByBooker(Integer bookerId);
}
