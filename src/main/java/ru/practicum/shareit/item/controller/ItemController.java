package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.comment.Comment;
import ru.practicum.shareit.item.model.comment.CommentDto;
import ru.practicum.shareit.item.model.comment.CommentRequest;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoOut;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Integer id) {
        return itemService.createItem(itemDto, id);
    }

    @GetMapping
    public List<ItemDto> findAllByUser(@RequestHeader("X-Sharer-User-Id") Integer id) {
        return itemService.findAllByUser(id);
    }

    @GetMapping("/{id}")
    public ItemDtoOut findItemById(@PathVariable("id") Integer id, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.findItemById(id, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable("id") Integer id, @RequestBody @Valid ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.updateItem(id, itemDto, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable("id") Integer id, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        itemService.deleteItemById(id, userId);
    }

    @GetMapping("/search")
    public List<ItemDto> findItemByNameOrDescription(@RequestParam("text") String text) {
        return itemService.findItemsByNameOrDescription(text);
    }

    @PostMapping("/{id}/comment")
    public CommentDto addComment(@PathVariable("id") Integer itemId, @RequestBody CommentRequest text, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.addComment(text.getText(), itemId, userId);
    }
}
