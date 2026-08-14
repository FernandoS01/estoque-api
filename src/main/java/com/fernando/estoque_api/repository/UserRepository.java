package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByIdAndDeletedAtIsNull(Long id);
    Optional<User> findByEmailAndDeletedAtIsNull(String id);
    List<User> findByDeletedAtIsNull();
}
