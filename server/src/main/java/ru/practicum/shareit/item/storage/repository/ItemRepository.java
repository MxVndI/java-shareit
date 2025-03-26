package ru.practicum.shareit.item.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByOwnerId(Integer userId);

    @Query(value = "SELECT *" +
            "FROM Items i " +
            "WHERE LOWER(i.name) LIKE LOWER(?1) OR LOWER(i.description) LIKE LOWER(?2)", nativeQuery = true)
    List<Item> findByNameContainingOrDescriptionContaining(String name, String description);

    List<Item> findAllByRequestId(Integer request);
}
