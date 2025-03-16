package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.exception.booking.ItemNotAvailableException;
import ru.practicum.shareit.booking.model.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.booking.NotValidDateException;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.exception.item.UncorrectOwnerException;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.item.storage.repository.ItemRepository;
import ru.practicum.shareit.user.storage.repository.UserRepository;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingDto addBooking(BookingDto bookingDto, Integer userId) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStart(bookingDto.getStart());
        booking.setEnds(bookingDto.getEnd());
        booking.setItem(itemRepository.findById(bookingDto.getItemId()).orElseThrow(()
                -> new ItemNotFoundException("Предмет с id =" + bookingDto.getItemId() + " не найден")));
        if (!booking.getItem().getAvailable()) {
            throw new ItemNotAvailableException("Предмет недоступен");
        }
        if (booking.getStart().isAfter(booking.getEnds()) || booking.getStart().isEqual(booking.getEnds())
                || booking.getStart().isAfter(LocalDateTime.now()) || booking.getEnds().isBefore(LocalDateTime.now())) {
            throw new NotValidDateException("Указаны неверные сроки одолжения");
        }
        booking.setBooker(userRepository.findById(bookingDto.getBooker()).orElseThrow(() -> new UserNotFoundException("no user")));
        booking.setBookingStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    public BookingDto changeBookingStatus(Integer bookingId, Boolean approved, Integer ownerId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("temp");
        }
        if (approved) {
            booking.setBookingStatus(BookingStatus.APPROVED);
            booking.getItem().setAvailable(false);
        } else {
            booking.setBookingStatus(BookingStatus.REJECTED);
        }
        bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    public BookingDto getBookingById(Integer bookingId, Integer userId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        if (!booking.getItem().getOwner().getId().equals(userId) || !booking.getBooker().getId().equals(userId)) {
            throw new RuntimeException("temp");
        }
        return BookingMapper.toDto(booking);
    }
}
