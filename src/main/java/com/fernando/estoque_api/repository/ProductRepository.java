package com.fernando.estoque_api.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);
    Optional<Product> findBySkuDeletedAtIsNull(String sku);
    Optional<Product> findByIdDeletedAtIsNull(Long id);
    List<Product> findByDeletedAtIsNull();
}
