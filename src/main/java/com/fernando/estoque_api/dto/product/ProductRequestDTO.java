package com.fernando.estoque_api.dto.product;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class ProductRequestDTO {

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

}

