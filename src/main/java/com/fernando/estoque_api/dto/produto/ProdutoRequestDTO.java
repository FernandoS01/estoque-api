package com.fernando.estoque_api.dto.produto;

import java.math.BigDecimal;

public class ProdutoRequestDTO {
    
    private String sku;

    private String name;

    private String description;

    private BigDecimal price;
    
    private Integer stockAmount;

}
