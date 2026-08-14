package com.fernando.estoque_api.mapper;

import org.springframework.stereotype.Component;

import com.fernando.estoque_api.dto.product.ProductResponseDTO;
import com.fernando.estoque_api.entity.Product;

@Component
public class ProductMapper {
    public ProductResponseDTO toDTO(Product product){
    ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setSku(product.getSku());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockAmount(product.getStockAmount());
        response.setCreatedAt(product.getCreatedAt());
    return response;
    }
}
