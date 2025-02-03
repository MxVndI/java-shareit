package ru.practicum.shareit.item.storage.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.item.FailedItemSaveException;
import ru.practicum.shareit.exception.item.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.search.OrSpecification;
import ru.practicum.shareit.item.search.Specification;
import ru.practicum.shareit.item.search.impl.DescriptionSpecification;
import ru.practicum.shareit.item.search.impl.NameSpecification;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemStorage implements ItemStorage {
    private final LinkedList<Item> items = new LinkedList<>();

    @Override
    public Item save(Item item) {
        item.setId(getLastId());
        if (!isItemNameEmpty(item) && !isItemDescriptionEmpty(item) && !isItemAvailableEmpty(item)) {
            items.add(item);
            return item;
        } else {
            throw new FailedItemSaveException("Не удалось добавить предмет");
        }
    }

    @Override
    public Item updateItem(Item item) {
        Item existingItem = findItemById(item.getId());
        if (existingItem == null) {
            throw new ItemNotFoundException("Предмет с ID " + item.getId() + " не найден.");
        }
        if (!isItemNameEmpty(item)) {
            existingItem.setName(item.getName());
        }
        if (!isItemDescriptionEmpty(item)) {
            existingItem.setDescription(item.getDescription());
        }
        if (!isItemAvailableEmpty(item)) {
            existingItem.setAvailable(item.getAvailable());
        }
        return existingItem;
    }

    @Override
    public Item deleteItemById(Integer id) {
        Item existingItem = findItemById(id);
        items.remove(existingItem);
        return existingItem;
    }

    @Override
    public Item findItemById(Integer id) {
        Optional<Item> item = items.stream().filter(i -> Objects.equals(i.getId(), id)).findFirst();
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ItemNotFoundException("Предмет с ID " + id + " не найден.");
        }
    }

    @Override
    public List<Item> findAllByUser(Integer userId) {
        return items.stream().filter(i -> i.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findAll() {
        return items;
    }

    public List<Item> findAllByNameOrDescription(String text) {
        Specification<Item> nameSpec = new NameSpecification(text);
        Specification<Item> descriptionSpec = new DescriptionSpecification(text);
        Specification<Item> combinedSpec = new OrSpecification<>(nameSpec, descriptionSpec);
        return findAll().stream()
                .filter(combinedSpec::isSatisfiedBy)
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
    }

    private Integer getLastId() {
        if (items.isEmpty())
            return 1;
        return items.getLast().getId() + 1;
    }

    private boolean isItemNameEmpty(Item item) {
        return item.getName() == null || item.getName().isEmpty();
    }

    private boolean isItemDescriptionEmpty(Item item) {
        return item.getDescription() == null || item.getDescription().isEmpty();
    }

    private boolean isItemAvailableEmpty(Item item) {
        return item.getAvailable() == null;
    }

}
