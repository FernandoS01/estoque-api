package com.fernando.estoque_api.dto.sale;

import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.enums.SaleStatus;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

public class SaleResponseDTO {

    @Getter
    @Setter
    private Long id;
    
    @Getter
    @Setter
    private UserResponseDTO user;

    @Getter
    @Setter
    private ClientResponseDTO client;

    @Getter
    @Setter
    private List<SaleItemResponse> itens;

    @Getter
    @Setter
    private BigDecimal totalAmount;

    @Getter
    @Setter
    private SaleStatus status;

    @Getter
    @Setter
    private LocalDateTime soldAt;
    
}
