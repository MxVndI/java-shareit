package ru.practicum.shareit.item.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
