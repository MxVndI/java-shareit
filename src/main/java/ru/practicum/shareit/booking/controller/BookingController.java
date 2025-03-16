package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto addBooking(@RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return bookingService.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto changeBookingStatus(@PathVariable("bookingId") Integer bookingId,
                                          @RequestParam Boolean approved,
                                          @RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return bookingService.changeBookingStatus(bookingId, approved, ownerId);
    }

    @GetMapping("{bookingId}")
    public BookingDto getBookingById(@PathVariable("bookingId") Integer bookingId,
                                     @RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return bookingService.getBookingById(bookingId, ownerId);
    }
}
