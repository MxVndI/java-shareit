package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    //GET /items
    // просмотр владельцем списка всех его вещей
    @GetMapping
    public ResponseEntity<Object> getAllItemsOfUser(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Просмотр владельцем списка всех его вещей: userId={}", userId);
        return itemClient.getAllItemsOfUser(userId);
    }

    //GET /items/{itemId}
    // найти вещь по Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable Integer id,
                                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Найти вещь по id: itemId={}, userId={}", id, userId);
        return itemClient.getItemById(id, userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto,
                                         @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Добавить вещь: item={}, userId={}", itemDto, userId);
        return itemClient.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@Valid @RequestBody ItemDto itemDto,
                                         @PathVariable Integer itemId,
                                         @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Отредактировать вещь: itemId={}, userId={}", itemId, userId);
        return itemClient.update(itemDto, itemId, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@Valid @NotNull(message = "Текст поиска должен быть указан.") @RequestParam String text) {
        log.info("Поиск вещи потенциальным арендатором");
        return itemClient.searchItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,
                                                @PathVariable Integer itemId,
                                                @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Добавление комментария о вещи после аренды: comment={}, itemId={}, userId={}", commentDto, itemId, userId);
        return itemClient.createComment(commentDto, itemId, userId);
    }
}