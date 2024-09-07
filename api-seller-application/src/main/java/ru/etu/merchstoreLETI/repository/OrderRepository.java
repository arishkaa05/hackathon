package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Order;
import ru.etu.merchstoreLETI.domain.model.Product;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);

    List<Order> findByAddressContainingIgnoreCaseOrStatusContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );

}