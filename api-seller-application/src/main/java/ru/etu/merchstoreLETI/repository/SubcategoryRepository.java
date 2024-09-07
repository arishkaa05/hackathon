package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Product;
import ru.etu.merchstoreLETI.domain.model.Subcategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    List<Subcategory> findByProductsContaining(Product product);

    List<Subcategory> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );

    Optional<Subcategory> findByTitleUrl(String titleUrl);
}