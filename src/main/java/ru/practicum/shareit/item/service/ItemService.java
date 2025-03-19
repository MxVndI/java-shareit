package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.comment.Comment;
import ru.practicum.shareit.item.model.comment.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoOut;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Integer userId);

    ItemDtoOut findItemById(Integer id, Integer userId);

    List<ItemDto> findAllByUser(Integer userId);

    ItemDto updateItem(Integer id, ItemDto itemDto, Integer userId);

    void deleteItemById(Integer id, Integer userId);

    List<ItemDto> findItemsByNameOrDescription(String text);

    CommentDto addComment(String text, Integer itemId, Integer userId);
}
