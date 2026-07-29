package com.fernando.estoque_api.dto.venda;

import lombok.Getter;
import lombok.Setter;

public class ItemVendaDTO {

    @Getter
    @Setter
    private Long produtoId;

    @Getter
    @Setter
    private Integer quantity;

}
