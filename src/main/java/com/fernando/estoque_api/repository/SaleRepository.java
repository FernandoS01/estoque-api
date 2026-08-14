package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.estoque_api.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{

}
