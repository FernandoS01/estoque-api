package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.SaleItems;

public interface SaleItemRepository extends JpaRepository<SaleItems, Long>{

}
