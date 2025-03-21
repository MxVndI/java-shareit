package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemRequestDto {
    private Integer id;
    private String description;
    private LocalDateTime created;
}
