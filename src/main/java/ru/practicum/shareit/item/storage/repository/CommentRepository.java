package ru.practicum.shareit.item.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
