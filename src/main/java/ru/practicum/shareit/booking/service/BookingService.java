package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.exception.booking.BookingNotFoundException;
import ru.practicum.shareit.exception.booking.ItemNotAvailableException;
import ru.practicum.shareit.booking.model.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.booking.NotValidDateException;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.exception.item.UncorrectOwnerException;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingDto addBooking(BookingDto bookingDto, Integer ownerId) {
        User booker = userRepository.findById(bookingDto.getBooker()).orElseThrow(()
                -> new UserNotFoundException("Пользователь с id = " + bookingDto.getBooker() + " не найден"));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(()
                -> new ItemNotFoundException("Предмет с id =" + bookingDto.getItemId() + " не найден"));
        Booking booking = BookingMapper.toBooking(bookingDto, booker, item);
        bookingValidation(bookingDto, item, ownerId);
        bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    public BookingDto changeBookingStatus(Integer bookingId, Boolean approved, Integer ownerId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new BookingNotFoundException("Бронь не найдена"));
        isCorrectItemOwner(booking.getItem(), ownerId);
        if (approved) {
            booking.setBookingStatus(BookingStatus.APPROVED);
            booking.getItem().setAvailable(false);
        } else {
            booking.setBookingStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    public BookingDto getBookingById(Integer bookingId, Integer ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow(()
                -> new UserNotFoundException("Пользователь с id = " + ownerId + " не найден"));
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new BookingNotFoundException("Бронирование предмета не найдено"));
        if (!booking.getItem().getOwner().equals(user) || !booking.getBooker().equals(user)) {
            throw new UncorrectOwnerException("Указан неверный владелец");
        }
        return BookingMapper.toDto(booking);
    }

    private void bookingValidation(BookingDto bookingDto, Item item, Integer ownerId) {
        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Вещь недоступна для бронирования.");
        }
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new NotValidDateException("Дата окончания не может быть раньше или равна дате начала");
        }
        isCorrectItemOwner(item, ownerId);
    }

    private void isCorrectItemOwner(Item item, Integer ownerId) {
        if (!ownerId.equals(item.getOwner().getId())) {
            throw new UncorrectOwnerException("Указан неверный владелец");
        }
    }
}
