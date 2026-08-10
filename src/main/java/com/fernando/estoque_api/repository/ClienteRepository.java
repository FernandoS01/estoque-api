package com.fernando.estoque_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.Cliente;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    Optional<Cliente> findByIdAndDeletedAtIsNull(Long id);

    Optional<Cliente> findByCpfAndDeletedAtIsNull(String cpf);

    Optional<Cliente> findByEmailAndDeletedAtIsNull(String email);

    List<Cliente> findByDeletedAtIsNull();
}
