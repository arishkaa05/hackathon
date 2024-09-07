package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Comment;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long id);

    List<Comment> findByProductContainingIgnoreCaseOrMessageContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );

    List<Comment> findByProduct(Product product);

    List<Comment> findByUser(User user);

    @Query("SELECT c FROM Comment c WHERE " +
            "(:minRating IS NULL OR c.rating >= :minRating) AND " +
            "(:maxRating IS NULL OR c.rating <= :maxRating) AND " +
            "(:startDate IS NULL OR c.createDate >= :startDate) AND " +
            "(:endDate IS NULL OR c.createDate <= :endDate)")
    List<Comment> searchComments(
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}