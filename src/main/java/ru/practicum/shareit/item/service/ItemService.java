package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {
    Item createItem(ItemDto itemDto, Integer userId);

    Item findItemById(Integer id);

    List<Item> findAllByUser(Integer userId);

    Item updateItem(Integer id, ItemDto itemDto, Integer userId);

    Item deleteItemById(Integer id, Integer userId);

    List<Item> findItemsByNameOrDescription(String text);
}
