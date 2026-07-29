package com.fernando.estoque_api.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.estoque_api.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsBySku(String sku);
    Optional<Produto> findBySku(String sku);
    List<Produto> findByDeletedAtIsNull();

    Optional<Produto> findByIdAtDeletedAtIsNotNull(Long id);

    Optional<Produto> findBySkuAtAtDeletedAtIsNotNull(String sku);
}
