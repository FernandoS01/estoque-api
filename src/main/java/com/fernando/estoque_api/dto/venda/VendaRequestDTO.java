package com.fernando.estoque_api.dto.venda;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

public class VendaRequestDTO {
    
    @Getter
    @Setter
    private Long clienteId;

    @Getter
    @Setter
    private List<ItemVendaDTO> itens;

}
