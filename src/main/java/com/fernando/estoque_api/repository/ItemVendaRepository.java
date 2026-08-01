package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fernando.estoque_api.entity.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>{

}
