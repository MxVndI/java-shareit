package ru.practicum.shareit.item.search.impl;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.search.Specification;

public class NameSpecification implements Specification<Item> {
    private String searchText;

    public NameSpecification(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Item item) {
        return item.getName().toLowerCase().contains(searchText);
    }
}
