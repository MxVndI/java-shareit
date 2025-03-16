package ru.practicum.shareit.item.model.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Integer id;
    @Size(min = 3, max = 100, message = "Item name length must be in between 3 and 20")
    private String name;
    @Size(min = 10, message = "Item description length must be more than 10")
    private String description;
    private Boolean available;
    private Integer request;
    private LocalDate lastBooking;
    private LocalDate nextBooking;
}
