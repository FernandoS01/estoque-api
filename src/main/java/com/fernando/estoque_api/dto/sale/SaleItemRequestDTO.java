package com.fernando.estoque_api.dto.sale;

import lombok.Getter;
import lombok.Setter;

public class SaleItemRequestDTO {

    @Getter
    @Setter
    private Long productId;

    @Getter
    @Setter
    private Integer quantity;

}
