package com.fernando.estoque_api.dto.sale;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleRequestDTO {
    
    private Long clientId;

    private Long userId;

    private List<SaleItemRequestDTO> itens;

}
