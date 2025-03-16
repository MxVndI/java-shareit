package ru.practicum.shareit.item.model.mapper;

import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.comment.Comment;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoOut;

import java.util.List;

public class ItemMapper {
    public static ItemDtoOut toItemDto(Item item, BookingDto lastBooking, BookingDto nextBooking, List<Comment> comments) {
        return new ItemDtoOut(
                new ItemDto(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getAvailable(),
                        item.getRequest() != null ? item.getRequest().getId() : null),
                lastBooking,
                nextBooking,
                comments
        );
    }

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null);
    }
}
