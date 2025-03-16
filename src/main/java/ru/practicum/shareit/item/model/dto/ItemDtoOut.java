package ru.practicum.shareit.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.item.model.comment.Comment;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoOut {
    private ItemDto itemDto;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<Comment> comments;
}
