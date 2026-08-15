package com.fernando.estoque_api.dto.sale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemRequestDTO {

    private Long productId;

    private Integer quantity;

}
