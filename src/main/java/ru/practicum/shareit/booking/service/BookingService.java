package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.booking.model.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.storage.repository.ItemRepository;
import ru.practicum.shareit.user.storage.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingDto addBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(itemRepository.findById(bookingDto.getItem()).get());
        booking.setBooker(userRepository.findById(bookingDto.getBooker()).get());
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
