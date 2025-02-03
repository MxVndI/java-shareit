package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @UniqueElements
    private Integer id;
    @Size(min = 3, max = 100, message = "Item name length must be in between 3 and 20")
    private String name;
    @Size(min = 10, message = "Item description length must be more than 10")
    private String description;
    @NotNull
    private Boolean available;
    private User owner;
    private ItemRequest request;
}
