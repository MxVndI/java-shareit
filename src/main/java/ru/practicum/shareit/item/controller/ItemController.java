package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
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
    public Item createItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Integer id) {
        return itemService.createItem(itemDto, id);
    }

    @GetMapping
    public List<Item> findAllByUser(@RequestHeader("X-Sharer-User-Id") Integer id) {
        return itemService.findAllByUser(id);
    }

    @GetMapping("/{id}")
    public Item findItemById(@PathVariable("id") Integer id) {
        return itemService.findItemById(id);
    }

    @PatchMapping("/{id}")
    public Item updateItem(@PathVariable("id") Integer id, @RequestBody @Valid ItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.updateItem(id, itemDto, userId);
    }

    @DeleteMapping("/{id}")
    public Item deleteItemById(@PathVariable("id") Integer id, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.deleteItemById(id, userId);
    }

    @GetMapping("/search")
    public List<Item> findItemByNameOrDescription(@RequestParam("text") String text) {
        return itemService.findItemsByNameOrDescription(text);
    }
}
