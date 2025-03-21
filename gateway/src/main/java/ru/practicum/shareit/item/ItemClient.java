package ru.practicum.shareit.item;

import ru.practicum.shareit.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;


@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllItemsOfUser(Integer userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getItemById(Integer id, Integer userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> create(ItemDto itemDto, Integer userId) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> update(ItemDto itemDto, Integer itemId, Integer userId) {
        return patch("/" + itemId, userId, itemDto);
    }

    public ResponseEntity<Object> searchItem(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> createComment(CommentDto commentDto, Integer itemId, Integer userId) {
        return post("/" + itemId + "/comment", userId, null, commentDto);
    }
}