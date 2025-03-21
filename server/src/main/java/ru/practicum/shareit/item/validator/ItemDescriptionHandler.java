package ru.practicum.shareit.item.validator;

import ru.practicum.shareit.exception.item.FailedItemSaveException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

public class ItemDescriptionHandler implements ItemValidationHandler {
    private ItemValidationHandler next;

    @Override
    public void setNext(ItemValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(ItemDto itemDto, Item item, boolean isCreating) {
        if (isCreating) {
            if (isItemDescriptionEmpty(itemDto)) {
                throw new FailedItemSaveException("Описание предмета обязательно для создания.");
            } else {
                item.setDescription(itemDto.getDescription());
            }
        } else {
            if (!isItemDescriptionEmpty(itemDto)) {
                item.setDescription(itemDto.getDescription());
            }
        }

        if (next != null) {
            next.handle(itemDto, item, isCreating);
        }
    }

    private boolean isItemDescriptionEmpty(ItemDto itemDto) {
        return itemDto.getDescription() == null || itemDto.getDescription().isEmpty();
    }
}
