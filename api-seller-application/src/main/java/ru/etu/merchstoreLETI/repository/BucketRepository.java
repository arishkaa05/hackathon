package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Bucket;
import ru.etu.merchstoreLETI.domain.model.Category;
import ru.etu.merchstoreLETI.domain.model.Product;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    List<Bucket> findByUserId(Long id);

    List<Bucket> findByProductsContaining(Product product);

    List<Bucket> findByTotalCostContainingIgnoreCaseOrAmountProductsContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );
}