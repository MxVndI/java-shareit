package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addNewRequest(@RequestBody ItemRequestDto itemRequestDto,
                                        @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.addNewRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDtoWithAnswers> getUserItemRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getUserItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestDtoWithAnswers getItemRequestById(@PathVariable("id") Integer requestId, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getItemRequestById(requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDtoWithAnswers> getItemRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getItemRequests(userId);
    }
}
