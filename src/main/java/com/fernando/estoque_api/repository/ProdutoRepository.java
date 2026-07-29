package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.estoque_api.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
