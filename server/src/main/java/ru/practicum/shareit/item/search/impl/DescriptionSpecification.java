package ru.practicum.shareit.item.search.impl;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.search.Specification;

public class DescriptionSpecification implements Specification<Item> {
    private String searchText;

    public DescriptionSpecification(String searchText) {
        this.searchText = searchText.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Item item) {
        return item.getDescription().toLowerCase().contains(searchText);
    }
}