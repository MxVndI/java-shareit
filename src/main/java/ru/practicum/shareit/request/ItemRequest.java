package ru.practicum.shareit.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    @UniqueElements
    private Integer id;
    @Size(min = 10, message = "ItemRequest description length must be more than 10")
    private String description;
    private User requestor;
    private LocalDateTime created;
}
