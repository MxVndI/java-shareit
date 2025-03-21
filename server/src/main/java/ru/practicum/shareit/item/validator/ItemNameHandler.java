package ru.practicum.shareit.item.validator;

import ru.practicum.shareit.exception.item.FailedItemSaveException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

public class ItemNameHandler implements ItemValidationHandler {
    private ItemValidationHandler next;

    @Override
    public void setNext(ItemValidationHandler next) {
        this.next = next;
    }

    @Override
    public void handle(ItemDto itemDto, Item item, boolean isCreating) {
        if (isCreating) {
            if (isItemNameEmpty(itemDto)) {
                throw new FailedItemSaveException("Имя предмета обязательно для создания.");
            } else {
                item.setName(itemDto.getName());
            }
        } else {
            if (!isItemNameEmpty(itemDto)) {
                item.setName(itemDto.getName());
            }
        }
        if (next != null) {
            next.handle(itemDto, item, isCreating);
        }
    }

    private boolean isItemNameEmpty(ItemDto itemDto) {
        return itemDto.getName() == null || itemDto.getName().isEmpty();
    }
}
