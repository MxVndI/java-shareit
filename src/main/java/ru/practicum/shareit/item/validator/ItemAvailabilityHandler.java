package ru.practicum.shareit.item.validator;

import ru.practicum.shareit.exception.item.FailedItemSaveException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

public class ItemAvailabilityHandler implements ItemValidationHandler {
    private ItemValidationHandler next;

    @Override
    public void setNext(ItemValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(ItemDto itemDto, Item item, boolean isCreating) {
        if (isCreating) {
            if (isItemAvailableEmpty(itemDto)) {
                throw new FailedItemSaveException("Доступность предмета обязательна для создания.");
            } else {
                item.setAvailable(itemDto.getAvailable());
            }
        } else {
            if (!isItemAvailableEmpty(itemDto)) {
                item.setAvailable(itemDto.getAvailable());
            }
        }
        if (next != null) {
            next.handle(itemDto, item, isCreating);
        }
    }

    private boolean isItemAvailableEmpty(ItemDto itemDto) {
        return itemDto.getAvailable() == null;
    }
}
