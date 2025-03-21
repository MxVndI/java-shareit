package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody BookingDto bookingDto,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Добавление нового запроса на бронирование: booking {}, userId={}", bookingDto, userId);
        return bookingClient.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approved(@Valid @RequestParam Boolean approved,
                                           @PathVariable Long bookingId,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Подтверждение или отклонение запроса на бронирование: approved {}, bookingId={}, userId={}",
                approved, bookingId, userId);
        return bookingClient.approved(approved, bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable Long bookingId,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получение запроса на бронирование по Id: bookingId={}, userId={}", bookingId, userId);
        return bookingClient.getBookingById(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findBookingByBooker(@Valid @RequestParam(defaultValue = "ALL") BookingState state,
                                                      @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получение списка всех бронирований текущего пользователя: state={}, userId={}", state, userId);
        return bookingClient.findBookingByBooker(state, userId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findBookingByOwner(@Valid @RequestParam(defaultValue = "ALL") BookingState state,
                                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получение списка всех бронирований текущего пользователя: state={}, userId={}", state, userId);
        return bookingClient.findBookingByOwner(state, userId);
    }
}