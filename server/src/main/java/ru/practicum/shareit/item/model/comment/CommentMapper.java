package ru.practicum.shareit.item.model.comment;

public class CommentMapper {
    public static CommentDto toItemDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }
}
