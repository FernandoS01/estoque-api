package com.fernando.estoque_api.dto.sale;

import java.math.BigDecimal;

import com.fernando.estoque_api.dto.product.ProductResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemResponse {

    private Long id;

    private ProductResponseDTO product;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}
