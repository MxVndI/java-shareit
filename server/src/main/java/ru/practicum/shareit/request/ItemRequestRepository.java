package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Integer> {
    List<ItemRequest> findAllByRequesterId(Integer requesterId);

    @Query("SELECT ir FROM ItemRequest ir WHERE ir.requester.id <> ?1 ORDER BY ir.created ASC")
    List<ItemRequest> findAllByRequesterIdNotOrderByCreatedAsc(Integer requesterId);
}
