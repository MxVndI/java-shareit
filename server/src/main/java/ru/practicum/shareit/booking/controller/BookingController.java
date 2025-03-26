package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoOut;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoOut addBooking(@RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return bookingService.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoOut changeBookingStatus(@PathVariable("bookingId") Integer bookingId,
                                          @RequestParam Boolean approved,
                                          @RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return bookingService.changeBookingStatus(bookingId, approved, ownerId);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoOut getBookingById(@PathVariable("bookingId") Integer bookingId,
                                     @RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return bookingService.getBookingById(bookingId, ownerId);
    }

    @GetMapping("/owner")
    public List<BookingDtoOut> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return bookingService.getAllBookingsByOwner(ownerId);
    }

    @GetMapping
    public List<BookingDtoOut> getAllBookingsByBooker(@RequestHeader("X-Sharer-User-Id") Integer bookerId) {
        return bookingService.getAllBookingsByBooker(bookerId);
    }
}
