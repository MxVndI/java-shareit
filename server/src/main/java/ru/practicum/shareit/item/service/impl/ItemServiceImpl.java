package ru.practicum.shareit.item.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.booking.BookingIsNotEndedException;
import ru.practicum.shareit.exception.booking.BookingNotFoundException;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.exception.item.UncorrectOwnerException;
import ru.practicum.shareit.exception.request.ItemRequestNotFound;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.comment.Comment;
import ru.practicum.shareit.item.model.comment.CommentDto;
import ru.practicum.shareit.item.model.comment.CommentMapper;
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
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.repository.UserRepository;

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
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto, Integer userId) {
        Item item;
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Нет пользователя"));
        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemDto.getRequestId())
                    .orElseThrow(() -> new ItemRequestNotFound("Нет бронирования"));
            item = ItemMapper.toItem(itemDto, user, itemRequest);
        } else {
            item = new Item();
        }
        ItemValidationHandler validationChain = createValidationChain();
        validationChain.handle(itemDto, item, true);
        item.setOwner(userStorage.findById(userId).orElseThrow(()
                -> new UserNotFoundException("Пользователь с id = " + userId + " не найден")));
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public ItemDtoOut findItemById(Integer id, Integer userId) {
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Нет пользователя"));
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new ItemNotFoundException("Предмет с id = " + id + " не найден"));
        Booking lastBooking = bookingRepository.getLastBooking(id, LocalDateTime.now()).orElse(null);
        Booking nextBooking = bookingRepository.getNextBooking(id, LocalDateTime.now()).orElse(null);
        List<CommentDto> comments = commentRepository.findAllByItemId(id).stream().map(CommentMapper::toItemDto).toList();
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
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Предмет с ID " + id + " не найден."));
        ItemValidationHandler validationChain = createValidationChain();
        validationChain.handle(itemDto, item, false);
        User owner = itemRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден")).getOwner();
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
        if (itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Предмет с ID " + id + " не найден."))
                .getOwner().getId().equals(userId)) {
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
        List<Item> items = itemRepository.findByNameContainingOrDescriptionContaining(text, text);
        return items.stream().filter(Item::getAvailable).map(ItemMapper::toItemDto).toList();
    }

    @Override
    public CommentDto addComment(String text, Integer itemId, Integer userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Предмет не найден"));
        User author = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Booking booking = bookingRepository.findByBookerIdAndItemId(userId, itemId)
                .orElseThrow(() -> new BookingNotFoundException("Нет брони"));
        if (booking.getEnds().isAfter(LocalDateTime.now())) {
            throw new BookingIsNotEndedException("Вы не можете оставить комментарий, когда бронь не закончится");
        }
        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setText(text);
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return CommentMapper.toItemDto(comment);
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
