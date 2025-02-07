package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Integer userId);

    ItemDto findItemById(Integer id);

    List<ItemDto> findAllByUser(Integer userId);

    ItemDto updateItem(Integer id, ItemDto itemDto, Integer userId);

    void deleteItemById(Integer id, Integer userId);

    List<ItemDto> findItemsByNameOrDescription(String text);
}
