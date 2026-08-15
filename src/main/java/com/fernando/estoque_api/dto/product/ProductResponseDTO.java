package com.fernando.estoque_api.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
 
    private String sku;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
