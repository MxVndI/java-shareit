package ru.practicum.shareit.booking.model.mapper;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnds(),
                booking.getBooker().getId(),
                booking.getItem().getId(),
                booking.getBookingStatus()
        );
    }

    public static Booking toBooking(BookingDto bookingDto, User user, Item item) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                user,
                BookingStatus.WAITING
        );
    }
}
