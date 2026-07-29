package com.fernando.estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.estoque_api.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

}
