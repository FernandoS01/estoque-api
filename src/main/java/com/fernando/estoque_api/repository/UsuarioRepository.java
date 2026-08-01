package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.Usuario;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByIdAndDeletedAtIsNull(Long id);
    Optional<Usuario> findByEmailAndDeletedAtIsNull(String id);
    List<Usuario> findByDeletedAtIsNull();
}
