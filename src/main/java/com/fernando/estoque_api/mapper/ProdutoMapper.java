package com.fernando.estoque_api.mapper;

import org.springframework.stereotype.Component;

import com.fernando.estoque_api.dto.produto.ProdutoResponseDTO;
import com.fernando.estoque_api.entity.Produto;

@Component
public class ProdutoMapper {
    public ProdutoResponseDTO toDTO(Produto produto){
    ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setId(produto.getId());
        response.setSku(produto.getSku());
        response.setName(produto.getName());
        response.setDescription(produto.getDescription());
        response.setPrice(produto.getPrice());
        response.setStockAmount(produto.getStockAmount());
        response.setCreatedAt(produto.getCreatedAt());
    return response;
    }
}
