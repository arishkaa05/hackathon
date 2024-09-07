package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByTitle(String title);

    List<Category> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );

    List<Category> findByTitleUrl(String title);
}