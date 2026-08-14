package com.fernando.estoque_api.dto.sale;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

public class SaleRequestDTO {
    
    @Getter
    @Setter
    private Long clientId;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private List<SaleItemRequestDTO> itens;

}
