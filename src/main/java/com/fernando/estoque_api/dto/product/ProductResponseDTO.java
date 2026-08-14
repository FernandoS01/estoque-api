package com.fernando.estoque_api.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class ProductResponseDTO {

    @Getter
    @Setter
    private Long id;
 
    @Getter
    @Setter
    private String sku;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private BigDecimal price;

    @Getter
    @Setter
    private Integer stockAmount;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime updatedAt;

}
