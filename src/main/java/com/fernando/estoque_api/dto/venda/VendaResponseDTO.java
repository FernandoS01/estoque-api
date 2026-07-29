package com.fernando.estoque_api.dto.venda;

import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.enums.VendaStatus;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

public class VendaResponseDTO {

    private Long id;

    private ClienteResponseDTO cliente;

    private List<ItemVendaDTO> itens;

    private BigDecimal totalAmount;

    private VendaStatus status;

    private LocalDateTime soldAt;
    
}
