package ru.practicum.shareit.request;

import ru.practicum.shareit.item.model.dto.ItemDtoAnswers;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRequestMapper {
    public static ItemRequestDto toDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getId(),
                itemRequest.getDescription(),
                LocalDateTime.now()
        );
    }

    public static ItemRequestDtoWithAnswers toDto(ItemRequest itemRequest, List<ItemDtoAnswers> items) {
        return new ItemRequestDtoWithAnswers(
                itemRequest.getId(),
                itemRequest.getDescription(),
                LocalDateTime.now(),
                items
        );
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User user) {
        return new ItemRequest(itemRequestDto.getDescription(),
                user,
                itemRequestDto.getCreated() == null ? LocalDateTime.now() : itemRequestDto.getCreated());
    }
}
