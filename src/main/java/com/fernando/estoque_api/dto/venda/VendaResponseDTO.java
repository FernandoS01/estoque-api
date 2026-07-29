package com.fernando.estoque_api.dto.venda;

import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.enums.VendaStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

public class VendaResponseDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private ClienteResponseDTO cliente;

    @Getter
    @Setter
    private List<ItemVendaDTO> itens;

    @Getter
    @Setter
    private BigDecimal totalAmount;

    @Getter
    @Setter
    private VendaStatus status;

    @Getter
    @Setter
    private LocalDateTime soldAt;
    
}
