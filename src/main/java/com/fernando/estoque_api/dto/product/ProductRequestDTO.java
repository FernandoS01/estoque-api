package com.fernando.estoque_api.dto.product;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    private String sku;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockAmount;

}

