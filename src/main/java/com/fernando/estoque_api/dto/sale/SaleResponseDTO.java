package com.fernando.estoque_api.dto.sale;

import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.enums.SaleStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class SaleResponseDTO {

    private Long id;

    private UserResponseDTO user;

    private ClientResponseDTO client;

    private List<SaleItemResponse> itens;

    private BigDecimal totalAmount;

    private SaleStatus status;

    private LocalDateTime soldAt;
    
}
