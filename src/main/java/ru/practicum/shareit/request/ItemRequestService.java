package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.user.UserNotFoundException;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoAnswers;
import ru.practicum.shareit.item.model.mapper.ItemMapper;
import ru.practicum.shareit.item.storage.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemRequestDto addNewRequest(ItemRequestDto itemRequestDto, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("no user"));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user);
        itemRequestRepository.save(itemRequest);
        return ItemRequestMapper.toDto(itemRequest);
    }

    public List<ItemRequestDto> getUserItemRequests(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("no user"));
        itemRequestRepository.findAllByRequesterId(userId);
        return itemRequestRepository.findAllByRequesterId(userId).stream().map(ItemRequestMapper::toDto).toList();
    }

    public ItemRequestDto getItemRequestById(Integer requestId) {
        List<ItemDtoAnswers> items = itemRepository.findAllByRequestId(requestId).stream().map(ItemMapper::toItemDtoAnswers).toList();
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow();
        return ItemRequestMapper.toDto(itemRequest, items);
    }

    public List<ItemRequestDtoWithAnswers> getItemRequests() {
        List<ItemRequest> requests = itemRequestRepository.findAll();
        List<ItemRequestDtoWithAnswers> requestsOut = new ArrayList<>();
        for (ItemRequest request: requests) {
            List<ItemDtoAnswers> items = itemRepository.findAllByRequestId(request.getId()).stream().map(ItemMapper::toItemDtoAnswers).toList();
            requestsOut.add(ItemRequestMapper.toDto(request, items));
        }
        return requestsOut;
    }
}
