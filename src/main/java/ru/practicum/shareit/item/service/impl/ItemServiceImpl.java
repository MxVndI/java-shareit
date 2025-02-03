package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.item.UncorrectOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public Item createItem(ItemDto itemDto, Integer userId) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setOwner(userService.findUserById(userId));
        item.setAvailable(itemDto.getAvailable());
        return itemStorage.save(item);
    }

    @Override
    public Item findItemById(Integer id) {
        return itemStorage.findItemById(id);
    }

    @Override
    public List<Item> findAllByUser(Integer userId) {
        return itemStorage.findAllByUser(userId);
    }

    @Override
    public Item updateItem(Integer id, ItemDto itemDto, Integer userId) {
        Item item = new Item();
        item.setId(id);
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        User owner = itemStorage.findItemById(id).getOwner();
        item.setOwner(owner);
        if (item.getOwner().getId().equals(userId)) {
            return itemStorage.updateItem(item);
        } else {
            throw new UncorrectOwnerException("Указан неверный владелец предмета");
        }
    }

    @Override
    public Item deleteItemById(Integer id, Integer userId) {
        if (itemStorage.findItemById(id).getOwner().getId().equals(userId)) {
            return itemStorage.deleteItemById(id);
        } else {
            throw new UncorrectOwnerException("Указан неверный владелец предмета");
        }
    }

    @Override
    public List<Item> findItemsByNameOrDescription(String text) {
        if (text == null || text.isEmpty()) {
            return new LinkedList<>();
        }
        return itemStorage.findAllByNameOrDescription(text);
    }

}
