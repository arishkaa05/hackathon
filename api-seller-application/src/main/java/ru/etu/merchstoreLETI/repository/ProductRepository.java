package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );

    List<Product> findByNewProduct(boolean b, PageRequest pageRequest);

    @Query("SELECT p FROM Product p WHERE " +
            "(:minOldPrice IS NULL OR p.oldPrice >= :minOldPrice) AND " +
            "(:maxOldPrice IS NULL OR p.oldPrice <= :maxOldPrice) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("minOldPrice") BigDecimal minOldPrice,
                                 @Param("maxOldPrice") BigDecimal maxOldPrice,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice);

    Optional<Product> findByTitleForUrl(String title);
}