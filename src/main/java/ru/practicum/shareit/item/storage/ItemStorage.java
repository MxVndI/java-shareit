package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item save(Item item);

    Item updateItem(Item item);

    Item deleteItemById(Integer id);

    Item findItemById(Integer id);

    List<Item> findAllByUser(Integer userId);

    List<Item> findAll();

    List<Item> findAllByNameOrDescription(String text);
}
