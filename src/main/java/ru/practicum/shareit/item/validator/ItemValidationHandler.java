package ru.practicum.shareit.item.validator;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

public interface ItemValidationHandler {
    void setNext(ItemValidationHandler next);

    void handle(ItemDto itemDto, Item item, boolean isCreating) throws IllegalArgumentException;
}