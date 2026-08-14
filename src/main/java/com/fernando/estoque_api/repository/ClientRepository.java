package com.fernando.estoque_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.Client;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>{

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    Optional<Client> findByIdAndDeletedAtIsNull(Long id);

    Optional<Client> findByCpfAndDeletedAtIsNull(String cpf);

    Optional<Client> findByEmailAndDeletedAtIsNull(String email);

    List<Client> findByDeletedAtIsNull();
}
