package ru.practicum.shareit.item.storage.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.search.OrSpecification;
import ru.practicum.shareit.item.search.Specification;
import ru.practicum.shareit.item.search.impl.DescriptionSpecification;
import ru.practicum.shareit.item.search.impl.NameSpecification;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Repository
public class InMemoryItemStorage implements ItemStorage {
    private final HashMap<Integer, Item> items = new HashMap<>();

    @Override
    public Item save(Item item) {
        item.setId(getLastId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        return items.replace(item.getId(), item);
    }

    @Override
    public void deleteItemById(Integer id) {
        items.remove(id);
    }

    @Override
    public Item findItemById(Integer id) {
        Optional<Item> item = Optional.ofNullable(items.get(id));
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ItemNotFoundException("Предмет с ID " + id + " не найден.");
        }
    }

    @Override
    public List<Item> findAllByUser(Integer userId) {
        List<Item> userItems = new ArrayList<>();
        for (Item item: items.values()) {
            if (item.getOwner().getId().equals(userId)) {
                userItems.add(item);
            }
        }
        return userItems;
    }

    @Override
    public List<Item> findAll() {
        return items.values().stream().toList();
    }

    @Override
    public List<Item> findAllByNameOrDescription(String text) {
        Specification<Item> nameSpec = new NameSpecification(text);
        Specification<Item> descriptionSpec = new DescriptionSpecification(text);
        Specification<Item> combinedSpec = new OrSpecification<>(nameSpec, descriptionSpec);
        return findAll().stream()
                .filter(combinedSpec::isSatisfiedBy)
                .filter(Item::getAvailable)
                .toList();
    }

    private Integer getLastId() {
        if (items.isEmpty())
            return 1;
        List<Item> itemList = new ArrayList<>(items.values());
        Item lastItem = itemList.getLast();
        return lastItem != null ? lastItem.getId() + 1 : 1;
    }

}
