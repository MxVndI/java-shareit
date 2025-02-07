package ru.practicum.shareit.item.storage.impl;

import org.springframework.stereotype.Repository;
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

@Repository
public class InMemoryItemStorage implements ItemStorage {
    private final LinkedList<Item> items = new LinkedList<>();
    // я оставил линкед лист, потому что нам нужны id внутри класса item. Если переводить в хэшу, надо что-то в ключ
    // выводить, а что? id? тогда какая-то фигня выходит и там и там один и тот же id.. не вижу смысла, утяжеление только

    @Override
    public Item save(Item item) {
        item.setId(getLastId());
        items.add(item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        return items.set(item.getId()-1, item);
    }

    @Override
    public void deleteItemById(Integer id) {
        Item existingItem = findItemById(id);
        items.remove(existingItem);
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
        return items.stream().filter(i -> i.getOwner().getId().equals(userId)).toList();
    }

    @Override
    public List<Item> findAll() {
        return items;
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
        return items.getLast().getId() + 1;
    }
}
