package ru.etu.merchstoreLETI.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.etu.merchstoreLETI.domain.model.Subcategory;
import ru.etu.merchstoreLETI.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User>  findUserByEmail(String email);

  List<User> findByCityContainingIgnoreCaseOrUsernameContainingIgnoreCase(
          String key1, String key2, Pageable pageable
  );

  @Query("SELECT u FROM User u WHERE " +
          "(:minCreateDate IS NULL OR u.createDate >= :minCreateDate) AND " +
          "(:maxCreateDate IS NULL OR u.createDate <= :maxCreateDate) ")
    List<User> searchUsers(
          @Param("minCreateDate") LocalDateTime minCreateDate,
          @Param("maxCreateDate") LocalDateTime maxCreateDate);
}