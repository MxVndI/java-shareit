package ru.practicum.shareit.item.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.exception.item.UncorrectOwnerException;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.comment.Comment;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoOut;
import ru.practicum.shareit.item.model.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.storage.repository.CommentRepository;
import ru.practicum.shareit.item.storage.repository.ItemRepository;
import ru.practicum.shareit.item.validator.ItemAvailabilityHandler;
import ru.practicum.shareit.item.validator.ItemDescriptionHandler;
import ru.practicum.shareit.item.validator.ItemNameHandler;
import ru.practicum.shareit.item.validator.ItemValidationHandler;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userStorage;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto, Integer userId) {
        Item item = new Item();
        ItemValidationHandler validationChain = createValidationChain();
        validationChain.handle(itemDto, item, true);
        item.setOwner(userStorage.findById(userId).orElseThrow(()
                -> new UserNotFoundException("Пользователь с id = " + userId + " не найден")));
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public ItemDtoOut findItemById(Integer id, Integer userId) {
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new ItemNotFoundException("Предмет с id = " + id + " не найден"));
        Booking lastBooking = bookingRepository.getLastBooking(id, LocalDateTime.now()).orElseThrow(() -> new RuntimeException("temp"));
        Booking nextBooking = bookingRepository.getNextBooking(id, LocalDateTime.now()).orElseThrow(() -> new RuntimeException("temp"));
        List<Comment> comments = commentRepository.findAllByItemId(id);
        return ItemMapper.toItemDto(item, BookingMapper.toDto(lastBooking), BookingMapper.toDto(nextBooking), comments);
    }

    @Override
    public List<ItemDto> findAllByUser(Integer userId) {
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        List<ItemDto> userItemsDto = new ArrayList<>();
        for (Item item : items) {
            userItemsDto.add(ItemMapper.toItemDto(item));
        }
        return userItemsDto;
    }

    @Override
    public ItemDto updateItem(Integer id, ItemDto itemDto, Integer userId) {
        Item item = itemRepository.findById(id).get();
        if (item == null) {
            throw new ItemNotFoundException("Предмет с ID " + item.getId() + " не найден.");
        }
        ItemValidationHandler validationChain = createValidationChain();
        validationChain.handle(itemDto, item, false);
        User owner = itemRepository.findById(id).get().getOwner();
        item.setOwner(owner);
        item.setId(id);
        if (item.getOwner().getId().equals(userId)) {
            itemRepository.save(item);
            return itemDto;
        } else {
            throw new UncorrectOwnerException("Указан неверный владелец предмета");
        }
    }

    @Override
    public void deleteItemById(Integer id, Integer userId) {
        if (itemRepository.findById(id).get().getOwner().getId().equals(userId)) {
            itemRepository.deleteById(id);
        } else {
            throw new UncorrectOwnerException("Указан неверный владелец предмета");
        }
    }

    @Override
    public List<ItemDto> findItemsByNameOrDescription(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> items = itemRepository.findAllByNameOrDescription(text, text);
        List<ItemDto> userItemsDto = new ArrayList<>();
        for (Item item : items) {
            if (item.getAvailable()) {
                userItemsDto.add(ItemMapper.toItemDto(item));
            }
        }
        return userItemsDto;
    }

    private ItemValidationHandler createValidationChain() {
        ItemValidationHandler nameHandler = new ItemNameHandler();
        ItemValidationHandler descriptionHandler = new ItemDescriptionHandler();
        ItemValidationHandler availabilityHandler = new ItemAvailabilityHandler();
        nameHandler.setNext(descriptionHandler);
        descriptionHandler.setNext(availabilityHandler);
        return nameHandler;
    }
}
