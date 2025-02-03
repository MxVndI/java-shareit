package ru.practicum.shareit.item.search;

public interface Specification<T> {
    boolean isSatisfiedBy(T item);
}
