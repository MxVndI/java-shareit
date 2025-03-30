package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.user.UserNotFoundException;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user);
        return ItemRequestMapper.toDto(itemRequestRepository.save(itemRequest));
    }

    public List<ItemRequestDtoWithAnswers> getUserItemRequests(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        List<ItemRequest> temp = itemRequestRepository.findAllByRequesterId(userId);
        List<ItemRequestDtoWithAnswers> requests = new ArrayList<>();
        for (ItemRequest request: temp) {
            List<ItemDtoAnswers> items = itemRepository.findAllByRequestId(request.getId())
                    .stream().map(ItemMapper::toItemDtoAnswers).toList();
            requests.add(ItemRequestMapper.toDto(request, items));
        }
        return requests;
    }

    public ItemRequestDtoWithAnswers getItemRequestById(Integer requestId) {
        List<ItemDtoAnswers> items = itemRepository.findAllByRequestId(requestId).stream().map(ItemMapper::toItemDtoAnswers).toList();
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow();
        return ItemRequestMapper.toDto(itemRequest, items);
    }

    public List<ItemRequestDtoWithAnswers> getItemRequests(Integer userId) {
        List<ItemRequest> requests = itemRequestRepository.findAllByRequesterIdNotOrderByCreatedAsc(userId);
        List<ItemRequestDtoWithAnswers> requestsOut = new ArrayList<>();
        for (ItemRequest request: requests) {
            List<ItemDtoAnswers> items = itemRepository.findAllByRequestId(request.getId())
                    .stream().map(ItemMapper::toItemDtoAnswers).toList();
            requestsOut.add(ItemRequestMapper.toDto(request, items));
        }
        return requestsOut;
    }
}
