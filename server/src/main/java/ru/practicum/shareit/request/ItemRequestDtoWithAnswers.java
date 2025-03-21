package ru.practicum.shareit.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.dto.ItemDtoAnswers;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ItemRequestDtoWithAnswers extends ItemRequestDto {
    private List<ItemDtoAnswers> items;

    public ItemRequestDtoWithAnswers(Integer id,
                                     @Size(min = 10, message = "ItemRequest description length must be more than 10")
                                     String description, LocalDateTime now, List<ItemDtoAnswers> items) {
        super(id, description, now);
        this.items = items;
    }
}
